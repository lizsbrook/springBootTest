package com.shangde.gao;

import java.util.Arrays;
import java.util.Optional;

class Outer {

    Nested nested;

    Nested getNested() {

        return nested;

    }

};

class Nested {

    Inner inner;

    Inner getInner() {

        return inner;

    }

};

class Inner {

    String foo;

    String getFoo() {

        return foo;

    }

};


public class Test {

    public static void  dosome(String e)
    {
        System.out.println(e+"hello!");
    }

    public static void main(String[] args)
    {
        Outer outer = new Outer();
        outer.nested = new Nested();
        outer.nested.inner = new Inner();
        outer.nested.inner.foo="hello world!";
//        if (outer != null && outer.nested!= null && outer.nested.inner != null) {
//
//            System.out.println("result is not null "+outer.nested.inner.foo);
//        }
//        else
//        {
//            System.out.println("result is null");
//        }
//        Nested dst = new Nested();
//        dst.inner = new Inner();
//        dst.inner.foo="error!";
//        Nested  op = Optional.ofNullable(outer)
//                .filter(user1 -> {
//                    return user1.nested != null;
//                })
//                .filter(s->
//                        {
//                            return s.nested.inner ==null;
//                        }
//                )
//                .map(Outer::getNested)
//                .orElse(dst);
//
//        System.out.println(op.inner.foo);

//        Arrays.asList( "a", "b", "d" ).forEach(e -> dosome(e) );

//        Arrays.asList( "a", "b", "d" ).sort( ( e1, e2 ) -> {
//            int result = e1.compareTo( e2 );
//            return result;
//        } );

        Optional< String > fullName = Optional.ofNullable( null );
        System.out.println( "Full Name is set? " + fullName.isPresent() );
        System.out.println( fullName.map( s -> "Hey " + s + "!" ).orElse( "Hey Stranger!" ) );
    }
}
