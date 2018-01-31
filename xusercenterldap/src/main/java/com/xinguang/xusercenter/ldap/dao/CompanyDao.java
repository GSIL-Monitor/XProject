package com.xinguang.xusercenter.ldap.dao;

import com.xinguang.xusercenter.ldap.domain.CompanyLdap;
import org.springframework.ldap.core.AttributesMapper;
import org.springframework.ldap.core.LdapTemplate;

import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import java.util.List;

/**
 * Created by yangsh on 2017-04-19
 */
public class CompanyDao {

    private LdapTemplate ldapTemplate;

    public CompanyLdap find(String rdn) {
        return (CompanyLdap) this.ldapTemplate.lookup(rdn, new AttributesMapper(){
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
                return getCompanyLdap(attributes);
            }
        });
    }

    public List<CompanyLdap> search(String rdn, String fileter) {
        return this.ldapTemplate.search(rdn, fileter, new AttributesMapper() {
            public Object mapFromAttributes(Attributes attributes) throws NamingException {
            return getCompanyLdap(attributes);
            }
        });
    }
    private CompanyLdap getCompanyLdap(Attributes attributes) throws NamingException {
        CompanyLdap cl = new CompanyLdap();
        if (attributes != null) {
            Attribute dcAttr = attributes.get("dc");
            if (dcAttr != null) {
                cl.setDc((String) dcAttr.get());
            }
            Attribute oAttr = attributes.get("o");
            if (oAttr != null) {
                cl.setO((String) oAttr.get());
            }
            Attribute descriptionAttr = attributes.get("description");
            if (descriptionAttr != null) {
                cl.setDescription((String) descriptionAttr.get());
            }
            Attribute telephoneNumberAttr = attributes.get("telephoneNumber");
            if (telephoneNumberAttr != null) {
                cl.setTelephoneNumber((String) telephoneNumberAttr.get());
            }
            Attribute streetAttr = attributes.get("street");
            if (streetAttr != null) {
                cl.setStreet((String) streetAttr.get());
            }
        }
        return cl;
    }

    public void setLdapTemplate(LdapTemplate ldapTemplate) {
        this.ldapTemplate = ldapTemplate;
    }

}
