package shortvideo.declantea.me.security.authority;

import org.springframework.security.core.GrantedAuthority;

public enum AuthorityEnum implements GrantedAuthority {
    Customer(Code.Customer),
    Manager(Code.Manager),
    Admin(Code.Admin);

    private final String authority;

    AuthorityEnum(String authority) {
        this.authority = authority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public static class Code {
        public static final String Customer = "ROLE_CUSTOMER";
        public static final String Manager = "ROLE_MANAGER";
        public static final String Admin = "ROLE_ADMIN";
    }
}
