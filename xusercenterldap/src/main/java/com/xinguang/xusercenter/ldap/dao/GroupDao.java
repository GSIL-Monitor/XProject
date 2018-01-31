package com.xinguang.xusercenter.ldap.dao;

import com.xinguang.xusercenter.ldap.domain.GroupLdap;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.List;

/**
 * Created by yangsh on 2017-04-19
 */
public class GroupDao {

    private LdapTemplate ldapTemplate;

    public List<GroupLdap> search(String rdn, String fileter) {
        return this.ldapTemplate.search(rdn, fileter, new AttributesMapper() {
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return getGroupLdap(attributes);
            }
        });
    }

    private GroupLdap getGroupLdap(Attributes attributes) throws NamingException {
        GroupLdap gl = new GroupLdap();
        if (attributes != null) {
            Attribute gidNumberAttr = attributes.get("gidNumber");
            if (gidNumberAttr != null) {
                gl.setGidNumber((String) gidNumberAttr.get());
            }
            Attribute cnAttr = attributes.get("cn");
            if (cnAttr != null) {
                gl.setCn((String) cnAttr.get());
            }
            Attribute descriptionAttr = attributes.get("description");
            if (descriptionAttr != null) {
                gl.setDescription((String) descriptionAttr.get());
            }
        }
        return gl;
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

}
