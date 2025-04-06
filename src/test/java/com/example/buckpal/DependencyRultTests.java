package com.example.buckpal;

import com.tngtech.archunit.core.importer.ClassFileImporter;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

public class DependencyRultTests {
    @Test
    void domainLayerDoesNotDependOnApplicationLayer() {
        noClasses()
                .that()
                .resideInAPackage("com.example.buckpal.domain..")
                .should()
                .dependOnClassesThat()
                .resideInAnyPackage(
                        "com.example.buckpal.account.application.."
                )
                .check(
                        new ClassFileImporter()
                                .importPackages("com.example.buckpal")
                );
    }
}
