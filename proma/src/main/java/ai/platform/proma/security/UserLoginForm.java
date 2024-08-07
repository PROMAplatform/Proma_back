package ai.platform.proma.security;

import ai.platform.proma.domain.enums.Role;

public interface UserLoginForm {
    public String getId();
    public Role getRole();
}
