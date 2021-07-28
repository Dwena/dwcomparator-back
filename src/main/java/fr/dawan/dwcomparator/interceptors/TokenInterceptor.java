package fr.dawan.dwcomparator.interceptors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import fr.dawan.dwcomparator.tools.JwtTokenUtil;
import io.jsonwebtoken.Claims;

@Component
public class TokenInterceptor implements HandlerInterceptor {

	@Autowired
	private JwtTokenUtil jwtTokenUtil;

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
		System.out.println(">>>>>> inside Token Interceptor...");
		System.out.println("URI =" + request.getRequestURI());
		System.out.println("Header (authorization) :" + request.getHeader("Authorization"));
		if (!request.getMethod().equals("OPTIONS")) {
			if (!request.getRequestURI().equals("/authenticate")
					&& !request.getRequestURI().equals("/insert-example")) {
				String headerAuth = request.getHeader("Authorization");
				if (headerAuth == null || headerAuth.trim().equals("") || headerAuth.length() < 7) {
					throw new Exception("Erreur : jeton absent ou invalide !");
				}

				String token = headerAuth.substring(7);
				// validation le token et extraire les infos
				if (jwtTokenUtil.isTokenExpired(token))
					throw new Exception("Erreur : jeton expiré !");

				String email = jwtTokenUtil.getUsernameFromToken(token);
				if (!TokenSaver.tokensByEmail.containsKey(email) || !TokenSaver.tokensByEmail.get(email).equals(token))
					throw new Exception("Erreur : jeton non reconnu !");

				// TODO autres extractions du jeton ou autres traitements
				Claims claims = jwtTokenUtil.getAllClaimsFromToken(token);
				Long userId = claims.get("user_id", Long.class);
				//TODO réfléchir à un moyen de passer userId de TokenInterceptor 
				// à AuditInterceptor
			}
		}
		return true;
	}

}
