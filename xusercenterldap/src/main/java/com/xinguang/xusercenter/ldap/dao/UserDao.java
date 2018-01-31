package com.xinguang.xusercenter.ldap.dao;

import com.xinguang.xusercenter.ldap.domain.UserLdap;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.NamingException;
import javax.naming.directory.*;
import java.util.List;
import java.util.Map;

/**
 * Created by yangsh on 2017-04-19
 */
public class UserDao {

    private LdapTemplate ldapTemplate;

    public void add(String rdn, UserLdap ul) {
        Attribute ocAttr = new BasicAttribute("objectClass");
        ocAttr.add("top");
        ocAttr.add("posixAccount");
        ocAttr.add("shadowAccount");
        ocAttr.add("inetOrgPerson");

        Attributes attrs = new BasicAttributes();

        attrs.put(ocAttr);
        attrs.put("uidNumber", ul.getUidNumber().getBytes());
        attrs.put("uid", ul.getUid());
        attrs.put("userPassword", ul.getUserPassword());
        attrs.put("o", ul.getO());
        attrs.put("gidNumber", ul.getGidNumber().getBytes());
        attrs.put("cn", ul.getCn());
        attrs.put("sn", ul.getSn());
        attrs.put("givenName",ul.getGivenName());
        attrs.put("mobile", ul.getMobile());
        attrs.put("mail", ul.getMail());
        attrs.put("homeDirectory", ul.getHomeDirectory());
        attrs.put("loginShell", ul.getLoginShell());

        this.ldapTemplate.bind(rdn,null, attrs);
    }

    public void modfiyAttrs(String rdn, Map<String, Object> param) {
        ModificationItem[] modificationItems = new ModificationItem[param.size()];

        int i = 0;
        for (Map.Entry<String, Object> entry: param.entrySet()) {
            Attribute attr = new BasicAttribute(entry.getKey(), entry.getValue());
            ModificationItem item = new ModificationItem(DirContext.REPLACE_ATTRIBUTE, attr);

            modificationItems[i] = item;
            i ++;
        }

        this.ldapTemplate.modifyAttributes(rdn, modificationItems);
    }

    public void addAttrs(String rdn, Map<String, Object> param) {
        ModificationItem[] modificationItems = new ModificationItem[param.size()];

        int i = 0;
        for (Map.Entry<String, Object> entry: param.entrySet()) {
            Attribute attr = new BasicAttribute(entry.getKey(), entry.getValue());
            ModificationItem item = new ModificationItem(DirContext.ADD_ATTRIBUTE, attr);

            modificationItems[i] = item;
            i ++;
        }

        this.ldapTemplate.modifyAttributes(rdn, modificationItems);
    }

    public UserLdap find(String rdn) {
        return (UserLdap) this.ldapTemplate.lookup(rdn, new AttributesMapper(){
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return getUserLdap(attributes);
            }
        });
    }

    public List<UserLdap> search(String rdn, String fileter) {
        return this.ldapTemplate.search(rdn, fileter, new AttributesMapper() {
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return getUserLdap(attributes);
            }
        });
    }

    private UserLdap getUserLdap(Attributes attributes) throws NamingException {
        UserLdap ul = new UserLdap();
        if (attributes != null) {
            Attribute uidNumberAttr = attributes.get("uidNumber");
            if (uidNumberAttr != null) {
                ul.setUidNumber((String) uidNumberAttr.get());
            }
            Attribute uidAttr = attributes.get("uid");
            if (uidAttr != null) {
                ul.setUid((String) uidAttr.get());
            }
            Attribute oAttr = attributes.get("o");
            if (oAttr != null) {
                ul.setO((String) oAttr.get());
            }
            Attribute userPasswordAttr = attributes.get("userPassword");
            byte[] bytes = (byte[]) userPasswordAttr.get(0);
            if (userPasswordAttr != null) {
                ul.setUserPassword(new String(bytes));
            }
            Attribute cnAttr = attributes.get("cn");
            if (cnAttr != null) {
                ul.setCn((String) cnAttr.get());
            }
            Attribute mobileAttr = attributes.get("mobile");
            if (mobileAttr != null) {
                ul.setMobile((String) mobileAttr.get());
            }
            Attribute mailAttr = attributes.get("mail");
            if (mailAttr != null) {
                ul.setMail((String) mailAttr.get());
            }
        }
        return ul;
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

}
