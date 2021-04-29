package shortvideo.declantea.me.Enum;

import org.springframework.security.core.GrantedAuthority;

public enum AuthorityEnum implements GrantedAuthority {
    Admin(Code.Admin),
    Manager(Code.Manager),
    Customer(Code.Customer),
    Band(Code.Band);

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
        public static final String Band = "ROLE_Band";
    }
}
