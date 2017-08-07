package com.wizaord.money.web.rest.util;

import com.wizaord.money.domain.User;
import org.apache.commons.lang3.RandomStringUtils;

public class UserTool {

    public static final String DEFAULT_LOGIN = "johndoe";
    public static final String DEFAULT_EMAIL = "johndoe@localhost";
    public static final String DEFAULT_FIRSTNAME = "john";
    public static final String DEFAULT_LASTNAME = "doe";
    public static final String DEFAULT_IMAGEURL = "http://placehold.it/50x50";
    public static final String DEFAULT_LANGKEY = "en";

    /**
     * Simply create a default user.
     * Id is set to 1;
     * @return
     */
    public static User createUser() {
        User user = new com.wizaord.money.domain.User();
        user.setId(1L);
        user.setLogin(DEFAULT_LOGIN);
        user.setPassword(RandomStringUtils.random(60));
        user.setActivated(true);
        user.setEmail(DEFAULT_EMAIL);
        user.setFirstName(DEFAULT_FIRSTNAME);
        user.setLastName(DEFAULT_LASTNAME);
        user.setImageUrl(DEFAULT_IMAGEURL);
        user.setLangKey(DEFAULT_LANGKEY);
        return user;
    }
}
