package com.ertogrul.omsb2b.web.security;

import com.ertogrul.omsb2b.service.security.ManagerPrincipal;
import org.springframework.security.access.expression.SecurityExpressionRoot;
import org.springframework.security.access.expression.method.MethodSecurityExpressionOperations;
import org.springframework.security.core.Authentication;


public class CustomMethodSecurityExpressionRoot extends SecurityExpressionRoot implements MethodSecurityExpressionOperations
{
    private Object filterObject;
    private Object returnObject;
    private Object target;

    CustomMethodSecurityExpressionRoot(Authentication authentication)
    {
        super(authentication);
    }

    @Override
    public void setFilterObject(Object filterObject)
    {
        this.filterObject = filterObject;
    }

    @Override
    public Object getFilterObject() {
        return filterObject;
    }

    @Override
    public void setReturnObject(Object returnObject) {
        this.returnObject = returnObject;
    }

    @Override
    public Object getReturnObject() {
        return returnObject;
    }

    @Override
    public Object getThis() {
        return target;
    }

    public void setThis(Object target)
    {
        this.target = target;
    }

    public boolean endpointAccess(final String endpoint){
        final ManagerPrincipal principal = (ManagerPrincipal) this.getPrincipal(); //implement here if the role is ViewEntityName check if it has EditEntiyName authority if yes automatically return true
        if(principal.isSuperAdmin()){
            return true;
        }else{
            if(endpoint.startsWith("View")) { //for getting Rid of everytime Specifying ViewRole but ViewRole standalone should be required in the future use cases
                final String predicate = "Edit" + endpoint.substring(4);
                if (principal.getAuthorities().parallelStream().anyMatch(t -> t.getAuthority().equals(predicate))) return true;
                else return hasAnyAuthority(endpoint);
            }else {
                return hasAuthority(endpoint);
            }
        }
    }

    public boolean anyEndpointAccess(final String... endpoints){
        final ManagerPrincipal principal = (ManagerPrincipal) this.getPrincipal();
        if(principal.isSuperAdmin())return true;else return hasAnyAuthority(endpoints);
    }




}
