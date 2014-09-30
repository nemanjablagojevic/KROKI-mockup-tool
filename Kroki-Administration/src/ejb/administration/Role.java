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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import framework.AbstractEntity;

@Entity
@Table(name = "Role")
public class Role extends AbstractEntity implements Serializable {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private Integer id;

	@Column(name = "name", unique = false, nullable = false)
	private String name;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<UserRoles> userRoles = new HashSet<UserRoles>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "role")
	private Set<RolePermission> rolePermission = new HashSet<RolePermission>();

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "parentRole")
	private Set<Role> roles = new HashSet<Role>();

	@ManyToOne
	@JoinColumn(name = "parentRole", referencedColumnName = "id", nullable = true)
	private Role parentRole;

	public Role() {
	}

	public void addUserRoles(UserRoles entity) {
		if (entity != null) {
			if (!userRoles.contains(entity)) {
				entity.setRole(this);
				userRoles.add(entity);
			}
		}
	}

	public void removeUserRoles(UserRoles entity) {
		if (entity != null) {
			if (userRoles.contains(entity)) {
				entity.setRole(null);
				userRoles.remove(entity);
			}
		}
	}

	public void addRolePermission(RolePermission entity) {
		if (entity != null) {
			if (!rolePermission.contains(entity)) {
				entity.setRole(this);
				rolePermission.add(entity);
			}
		}
	}

	public void removeRolePermission(RolePermission entity) {
		if (entity != null) {
			if (rolePermission.contains(entity)) {
				entity.setRole(null);
				rolePermission.remove(entity);
			}
		}
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Set<UserRoles> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoles> userRoles) {
		this.userRoles = userRoles;
	}

	public Set<RolePermission> getRolePermission() {
		return rolePermission;
	}

	public void setRolePermission(Set<RolePermission> rolePermission) {
		this.rolePermission = rolePermission;
	}

	public Role getParentRole() {
		return parentRole;
	}

	public void setParentRole(Role parentRole) {
		this.parentRole = parentRole;
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
		list.add(name.toString());
		if (parentRole != null) {
			list.add(parentRole.toString());
		} else {
			list.add("");
		}

		return list.toArray();
	}

	@Override
	public String toString() {
		StringBuilder result = new StringBuilder();

		result.append(name + " ");

		return result.toString();
	}

}
