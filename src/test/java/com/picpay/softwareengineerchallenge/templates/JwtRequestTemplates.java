package com.picpay.softwareengineerchallenge.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.picpay.softwareengineerchallenge.controller.request.JwtRequest;

public class JwtRequestTemplates implements TemplateLoader {

    public static final String VALID_JWT_REQUEST = "VALID_JWT_REQUEST";
    public static final String EMPTY_JWT_REQUEST = "EMPTY_JWT_REQUEST";
    public static final String NULLABLE_JWT_REQUEST = "NULLABLE_JWT_REQUEST";
    public static final String MISSING_USERNAME_JWT_REQUEST = "MISSING_USERNAME_JWT_REQUEST";
    public static final String MISSING_PASSWORD_JWT_REQUEST = "MISSING_PASSWORD_JWT_REQUEST";

    @Override
    public void load() {
        Fixture.of(JwtRequest.class).addTemplate(VALID_JWT_REQUEST, new Rule() {{
            add("username", "diether.bein");
            add("password", "!picPAY@2021");
        }});
        Fixture.of(JwtRequest.class).addTemplate(EMPTY_JWT_REQUEST, new Rule() {{
            add("username", "");
            add("password", "");
        }});
        Fixture.of(JwtRequest.class).addTemplate(NULLABLE_JWT_REQUEST, new Rule() {{
            add("username", null);
            add("password", null);
        }});
        Fixture.of(JwtRequest.class).addTemplate(MISSING_USERNAME_JWT_REQUEST, new Rule() {{
            add("username", "");
            add("password", "!picPAY@2021");
        }});
        Fixture.of(JwtRequest.class).addTemplate(MISSING_PASSWORD_JWT_REQUEST, new Rule() {{
            add("username", "diether.bein");
            add("password", "");
        }});
    }
}
