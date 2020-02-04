package com.ssafy.model.dto;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import javax.persistence.ElementCollection;
import javax.persistence.FetchType;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import lombok.Builder;

/*
 SpringSecurity는 UserDetails 객체를 통해 권한 정보를 관리하기 때문에 
 User 클래스에 UserDetails 를 구현하고 추가 정보를 재정의 해야 합니다. 
 Entity와 UserDetails는 구분할 수도 같은 클래스에서 관리할 수도 있습니다. 
  여기에서는 같은 클래스에서 관리하는 방법을 사용하도록 하겠습니다. 
 */

/*
SpringSecurity의 보안을 적용하기 위해 User entity에
UserDetails Class를 상속받아 추가 정보를 재정의 합니다. 
roles는 회원이 가지고 있는 권한 정보이고, 
가입했을 때는 기본 “ROLE_USER”가 세팅됩니다. 

*/

//public class user implements UserDetails {
public class user implements UserDetails {
	private int user_no; 
	private String user_email; 
	private String user_pw; 
	private String user_id; 
	private String user_image; 
	private String user_desc;
	
	
	public user() {}
	
	public user(int user_no, String user_email, String user_pw, String user_id) {
		super();
		this.user_no = user_no;
		this.user_email = user_email;
		this.user_pw = user_pw;
		this.user_id = user_id;
	}
	
	public user(int user_no, String user_email, String user_pw, String user_id, String user_image, String user_desc) {
		super();
		this.user_no = user_no;
		this.user_email = user_email;
		this.user_pw = user_pw;
		this.user_id = user_id;
		this.user_image = user_image;
		this.user_desc = user_desc;
	}
	
	public int getUser_no() {
		return user_no;
	}
	public void setUser_no(int user_no) {
		this.user_no = user_no;
	}
	public String getUser_email() {
		return user_email;
	}
	public void setUser_email(String user_email) {
		this.user_email = user_email;
	}
	public String getUser_pw() {
		return user_pw;
	}
	public void setUser_pw(String user_pw) {
		this.user_pw = user_pw;
	}
	public String getUser_id() {
		return user_id;
	}
	public void setUser_id(String user_id) {
		this.user_id = user_id;
	}
	public String getUser_image() {
		return user_image;
	}
	public void setUser_image(String user_image) {
		this.user_image = user_image;
	}
	public String getUser_desc() {
		return user_desc;
	}
	public void setUser_desc(String user_desc) {
		this.user_desc = user_desc;
	}

	@Override
	public String toString() {
		return "user [user_no=" + user_no + ", user_email=" + user_email + ", user_pw=" + user_pw + ", user_id="
				+ user_id + ", user_image=" + user_image + ", user_desc=" + user_desc + "]";
	}
	
	//userDetails 추가부분
	
	@ElementCollection(fetch=FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	//권한은 회원당 여러 개가 세팅될 수 있으므로 Collection으로 선언합니다.
	/*
	 * @Override public Collection<? extends GrantedAuthority> getAuthorities() {
	 * return this.roles.stream() .map(SimpleGrantedAuthority::new)
	 * .collect(Collectors.toList()); }
	 */

	@Override
	public String getPassword() {
		// TODO Auto-generated method stub
		return null;
	}

	
	//getUsername은 security에서 사용하는 회원 구분 id입니다. 
	@Override
	public String getUsername() {
		return user_email;
	}

	// 다음 값들은 Security에서 사용하는 회원 상태 값입니다. 
	// 여기선 모두 사용 안 하므로 true로 설정합니다.
	
	//isAccountNonExpired : 계정이 만료가 안되었는지
	@Override
	public boolean isAccountNonExpired() {
		return true;
	}

	
	//isAccountNonLocked : 계정이 잠기지 않았는지
	@Override
	public boolean isAccountNonLocked() {
		return true;
	}

	//isCredentialsNonExpired : 계정 패스워드가 만료 안됬는지
	@Override
	public boolean isCredentialsNonExpired() {
		return true;
	}

	//isEnabled : 계정이 사용 가능한지
	@Override
	public boolean isEnabled() {
		return true;
	}

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		// TODO Auto-generated method stub
		return null;
	} 
	
}
