package ejb.administration;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import framework.AbstractEntity;

@Entity
@Table(name = "User")
public class User extends AbstractEntity implements Serializable {  

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;
	
		@Column(name="username", unique = false, nullable = false)
		private String username;
         
		@Column(name="password", unique = false, nullable = false)
		private String password;
         
    	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "user")
  		private Set<UserRoles> useringroup = new HashSet<UserRoles>();
         

	public User(){
	}

    	public void addUseringroup(UserRoles entity) {
    		if(entity != null) {
    			if(!useringroup.contains(entity)) {
    				entity.setUser(this);
    				useringroup.add(entity);
    			}
    		}
    	}
    	
    	public void removeUseringroup(UserRoles entity) {
    		if(entity != null) {
    			if(useringroup.contains(entity)) {
					entity.setUser(null);
    				useringroup.remove(entity);
    			}
    		}
    	}

      public String getUsername(){
           return username;
      }
      
      public void setUsername(String username){
           this.username = username;
      }
      
      public String getPassword(){
           return password;
      }
      
      public void setPassword(String password){
           this.password = password;
      }
      
      public Set<UserRoles> getUseringroup(){
           return useringroup;
      }
      
      public void setUseringroup( Set<UserRoles> useringroup){
           this.useringroup = useringroup;
      }
      

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	@Override
	public Object[] getValues() {	
		List<Object> list = new ArrayList<Object>();
		
		list.add(id);		
		list.add(username.toString());
		list.add(password.toString());
		 
		 return list.toArray();
	}
	
	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();
		
		result.append(username + " ");
		result.append(password + " ");
		
		return result.toString();
	}

}
