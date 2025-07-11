package PFE.CDSIR_AGENCY.config;

import PFE.CDSIR_AGENCY.filter.JwtAuthFilter;
import PFE.CDSIR_AGENCY.repository.ClientRepository;
import PFE.CDSIR_AGENCY.repository.AdministrateurRepository;
import PFE.CDSIR_AGENCY.entity.Administrateur;
import PFE.CDSIR_AGENCY.entity.Client;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
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
	private final AdministrateurRepository administrateurRepository;
	private final JwtUtils jwtUtils;

	@Value("${app.cors.allowed-origins}")
	private String allowedOrigins;
	
	@Value("${app.cors.allowed-methods}")
	private String allowedMethods;
	
	@Value("${app.cors.allowed-headers}")
	private String allowedHeaders;
	
	@Value("${app.cors.exposed-headers}")
	private String exposedHeaders;
	
	@Value("${app.cors.max-age:3600}")
	private Long maxAge;
	
	@Value("${app.cors.allow-credentials:true}")
	private Boolean allowCredentials;

	public SecurityConfig(ClientRepository clientRepository, AdministrateurRepository administrateurRepository, JwtUtils jwtUtils) {
		this.clientRepository = clientRepository;
		this.administrateurRepository = administrateurRepository;
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
						.frameOptions(frame -> frame.sameOrigin())
						.httpStrictTransportSecurity(hsts -> hsts
								.includeSubDomains(true)
								.preload(true)
								.maxAgeInSeconds(63072000))
						.referrerPolicy(referrer -> referrer.policy(ReferrerPolicyHeaderWriter.ReferrerPolicy.STRICT_ORIGIN_WHEN_CROSS_ORIGIN))
						.contentTypeOptions(contentType -> {})
				)

				// Autorisations des requêtes
				.authorizeHttpRequests(auth -> auth
				    // TRÈS IMPORTANT : Permet TOUTES les requêtes OPTIONS sans authentification en premier
				    .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
					.requestMatchers("/", "/error").permitAll()
						// AJOUTÉ : Permet les requêtes POST vers /api/colis sans authentification
						.requestMatchers(HttpMethod.POST, "/api/colis").permitAll()
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
						// TEMPORAIRE POUR DEBUG : Permet TOUTES les requêtes admin sans authentification
						.requestMatchers("/api/admin/**").permitAll()
						// Ancienne ligne commentée (à remettre en production) :
						// .requestMatchers("/api/admin/register").hasRole("ADMIN")
						// .requestMatchers("/api/admin/**").hasRole("ADMIN")
						.requestMatchers("/api/clients/me/**").hasRole("CLIENT")
						.requestMatchers("/api/reservation/client/**").hasAnyRole("CLIENT", "AGENT")
						.requestMatchers("/api/reservation/validate").hasRole("AGENT")
						.requestMatchers(
								// MODIFIÉ : "/api/colis" a été retiré d'ici car le POST est maintenant permitAll
								// Les autres méthodes (GET, PUT, DELETE) sur /api/colis/{id} nécessiteront toujours le rôle CLIENT
								// si elles ne sont pas explicitement permitAll.
								// "/api/colis", // <-- CETTE LIGNE A ÉTÉ RETIRÉE
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
		return username -> {
			Optional<Client> clientOptional = clientRepository.findByEmail(username);
			if (clientOptional.isPresent()) {
				return (UserDetails) clientOptional.get();
			}

			Optional<Administrateur> adminOptional = administrateurRepository.findByEmail(username);
			if (adminOptional.isPresent()) {
				return (UserDetails) adminOptional.get();
			}

			throw new UsernameNotFoundException("Utilisateur non trouvé: " + username);
		};
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
		
		List<String> origins = Arrays.asList(allowedOrigins.split(","));
		List<String> methods = Arrays.asList(allowedMethods.split(","));
		List<String> headers = Arrays.asList(allowedHeaders.split(","));
		List<String> exposedHeadersList = Arrays.asList(exposedHeaders.split(","));
		
		configuration.setAllowedOrigins(origins);
		configuration.setAllowedMethods(methods);
		configuration.setAllowedHeaders(headers);
		configuration.setExposedHeaders(exposedHeadersList);
		configuration.setAllowCredentials(allowCredentials);
		configuration.setMaxAge(maxAge);
		
		System.out.println("=== CORS CONFIGURATION DEBUG ===");
		System.out.println("Allowed Origins: " + origins);
		System.out.println("Allowed Methods: " + methods);
		System.out.println("Allowed Headers: " + headers);
		System.out.println("Allow Credentials: " + allowCredentials);
		System.out.println("================================");
		
		UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
		source.registerCorsConfiguration("/**", configuration);
		return source;
	}
}
