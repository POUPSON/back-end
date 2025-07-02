package PFE.CDSIR_AGENCY.config;

import PFE.CDSIR_AGENCY.filter.JwtAuthFilter;
import PFE.CDSIR_AGENCY.repository.ClientRepository;
import java.util.Arrays;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.annotation.web.configurers.HeadersConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.header.writers.XXssProtectionHeaderWriter;
import org.springframework.security.web.header.writers.ReferrerPolicyHeaderWriter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfig {
	private final ClientRepository clientRepository;
	private final JwtUtils jwtUtils;

	@Value("${app.cors.allowed-origins}")
	private String[] allowedOrigins;
	@Value("${app.cors.allowed-methods}")
	private String[] allowedMethods;
	@Value("${app.cors.allowed-headers}")
	private String[] allowedHeaders;
	@Value("${app.cors.exposed-headers}")
	private String[] exposedHeaders;

	public SecurityConfig(ClientRepository clientRepository, JwtUtils jwtUtils) {
		this.clientRepository = clientRepository;
		this.jwtUtils = jwtUtils;
	}

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
		http
				// Désactivation CSRF et configuration CORS
				.csrf(AbstractHttpConfigurer::disable)
				.cors(cors -> cors.configurationSource(corsConfigurationSource()))

				// Configuration des en-têtes de sécurité
				.headers(headers -> headers
						.xssProtection(xss -> xss.headerValue(XXssProtectionHeaderWriter.HeaderValue.ENABLED_MODE_BLOCK))
						.contentSecurityPolicy(csp -> csp.policyDirectives("default-src 'self'; script-src 'self' 'unsafe-inline'; style-src 'self' 'unsafe-inline'; img-src 'self' data:"))
						.frameOptions(frame -> frame.sameOrigin()) // ou .deny() selon vos besoins
						.httpStrictTransportSecurity(hsts -> hsts
								.includeSubDomains(true)
								.preload(true)
								.maxAgeInSeconds(63072000))
						.referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
						.contentTypeOptions(contentType -> {})
				)

				// Autorisations des requêtes
				.authorizeHttpRequests(auth -> auth
					   .requestMatchers("/", "/error").permitAll() // ✅ POUR CORRIGER LE 403 A LA RACINE

						.requestMatchers(
								"/api/clients/register",
								"/api/clients/login",
								"/api/clients/forgot-password",
								"/api/clients/reset-password",
								"/api/colis/tracking/**",
								"/api/agences/**",
								"/api/trajets/**",
								"/api/horaires/**",
								"/api/vehicules/**",
								"/api/voyages/**",
								"/api/reservation/validate/**",
								"/api/reservation/public/**",
								"/v3/api-docs/**",
								"/swagger-ui/**",
								"/swagger-ui.html"
						).permitAll()
						.requestMatchers("/api/admin/**").hasRole("ADMIN")
						.requestMatchers("/api/clients/me/**").hasRole("CLIENT")
						.requestMatchers("/api/reservation/client/**").hasAnyRole("CLIENT", "AGENT")
						.requestMatchers("/api/reservation/validate").hasRole("AGENT")
						.requestMatchers(
								"/api/colis",
								"/api/colis/{id}",
								"/api/colis/cancel/{id}",
								"/api/colis/my-sent-history",
								"/api/colis/my-received-history",
								"/api/reservations",
								"/api/reservations/{id}",
								"/api/reservations/client/**",
								"/api/reservations/my-history"
						).hasRole("CLIENT")
						.anyRequest().authenticated()
				)

				// Gestion des sessions
				.sessionManagement(session -> session
						.sessionCreationPolicy(SessionCreationPolicy.STATELESS))

				// Configuration du provider d'authentification et du filtre JWT
				.authenticationProvider(authenticationProvider())
				.addFilterBefore(jwtAuthFilter(), UsernamePasswordAuthenticationFilter.class);

		return http.build();
	}

	@Bean
	public UserDetailsService userDetailsService() {
		return username -> (UserDetails) clientRepository.findByEmail(username)
				.orElseThrow(() -> new UsernameNotFoundException("Client non trouvé: " + username));
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public AuthenticationProvider authenticationProvider() {
		DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
		authProvider.setUserDetailsService(userDetailsService());
		authProvider.setPasswordEncoder(passwordEncoder());
		return authProvider;
	}

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}

	@Bean
	public JwtAuthFilter jwtAuthFilter() {
		return new JwtAuthFilter(jwtUtils, userDetailsService());
	}

	@Bean
	
public CorsConfigurationSource corsConfigurationSource() {
    CorsConfiguration configuration = new CorsConfiguration();
    configuration.setAllowedOrigins(Arrays.asList(allowedOrigins)); // ✅ CORRECTION
    configuration.setAllowedMethods(Arrays.asList(allowedMethods));
    configuration.setAllowedHeaders(Arrays.asList(allowedHeaders));
    configuration.setExposedHeaders(Arrays.asList(exposedHeaders));
    configuration.setAllowCredentials(true);
    UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
    source.registerCorsConfiguration("/**", configuration); // attention ici : /** et pas /
    return source;
}

}
