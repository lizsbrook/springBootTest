package com.yuning.gao.domain;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;


@Getter
@Setter
@ToString
//@Data 注解 会包含@Getter @Setter @RequiredArgsConstructor @ToString @EqualsAndHashCode这5个注解
public class LombakTest {
    private Integer id;

    private String name;

    public static void main(String[] args)
    {
        LombakTest lombakTest = new LombakTest();
        lombakTest.setId(111);
        lombakTest.setName("hello lombak");
        System.out.println(lombakTest);

    }
}

