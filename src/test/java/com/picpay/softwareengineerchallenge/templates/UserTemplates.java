package com.picpay.softwareengineerchallenge.templates;

import br.com.six2six.fixturefactory.Fixture;
import br.com.six2six.fixturefactory.Rule;
import br.com.six2six.fixturefactory.loader.TemplateLoader;
import com.picpay.softwareengineerchallenge.domain.User;

public class UserTemplates implements TemplateLoader {
    public static final String VALID_USER_1 = "VALID_USER_1";
    public static final String VALID_USER_2 = "VALID_USER_2";
    public static final String VALID_USER_3 = "VALID_USER_3";

    @Override
    public void load() {
        Fixture.of(User.class).addTemplate(VALID_USER_1, new Rule() {{
            add("id", "605d41e6d7c112ed4240a07a");
            add("uuid", "88e57169-1e16-4f52-92c0-3c3214481579");
            add("name", "Diether Bein");
            add("username", "diether.bein");
            add("relevanceLevel", 2);
            add("password", "!picPAY@2021");
        }});
        Fixture.of(User.class).addTemplate(VALID_USER_2, new Rule() {{
            add("id", "605d41e6d7c112ed4240a079");
            add("uuid", "99e4e36a-9aeb-4f2c-ba5e-7c9b4e03d616");
            add("name", "Diether Sampaio");
            add("username", "diether.sampaio");
            add("relevanceLevel", 0);
            add("password", "!picPAY@2021");
        }});
        Fixture.of(User.class).addTemplate(VALID_USER_3, new Rule() {{
            add("id", "605d41e6d7c112ed4240a079");
            add("uuid", "498ff55d-d6b6-48df-9f35-94304dc24020");
            add("name", "Diether Negreiros");
            add("username", "diether.negreiros");
            add("relevanceLevel", 0);
            add("password", "!picPAY@2020");
        }});
    }
}
