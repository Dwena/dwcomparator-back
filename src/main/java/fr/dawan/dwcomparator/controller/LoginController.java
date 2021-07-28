package fr.dawan.dwcomparator.controller;


import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import fr.dawan.dwcomparator.dto.LoginDto;
import fr.dawan.dwcomparator.dto.LoginResponseDto;
import fr.dawan.dwcomparator.dto.UserDto;
import fr.dawan.dwcomparator.interceptors.TokenSaver;
import fr.dawan.dwcomparator.services.UserService;
import fr.dawan.dwcomparator.tools.HashTools;
import fr.dawan.dwcomparator.tools.JwtTokenUtil;

@RestController
public class LoginController {

	@Autowired
	private UserService userService;

	
	@Autowired
	private JwtTokenUtil jwtTokenUtil;
	
	@CrossOrigin(origins = "http://localhost:8080")
	@PostMapping(value="/authenticate", consumes = "application/json")
	public ResponseEntity<?> checkLogin(@RequestBody LoginDto loginObj) throws Exception{
		
		UserDto cDto = userService.findByEmail(loginObj.getEmail());
		
		String hashedPwd = HashTools.hashSHA512(loginObj.getPassword());
		
		
		if(cDto !=null && cDto.getPassword().contentEquals(hashedPwd)) {
			
			//Fabrication du token en utilisant jjwt (librairie incluse dans le pom)
			Map<String, Object> claims = new HashMap<String, Object>();
			claims.put("user_id", cDto.getId());
			claims.put("user_fullName", cDto.getFullName());
			//ajouter les données que l'on souhaite
			String token = jwtTokenUtil.doGenerateToken(claims, loginObj.getEmail());
			TokenSaver.tokensByEmail.put(loginObj.getEmail(), token);
			
			return ResponseEntity.ok(new LoginResponseDto(cDto.getId(), cDto.getFirstName(), cDto.getLastName(), cDto.getEmail(), token));
		}else
			throw new Exception("Erreur : identifiants incorrects !");
		
	}


	
}
