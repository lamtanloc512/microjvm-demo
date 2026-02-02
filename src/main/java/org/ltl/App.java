package org.ltl;

import org.microjvm.boot.MicroJVM;
import org.microjvm.boot.annotation.MicroApplication;
import org.microjvm.data.annotation.EnableData;

@MicroApplication
@EnableData(provider = "jpa")
public class App {
    public static void main(String[] args) {
        MicroJVM.run(App.class, args);
    }
}
