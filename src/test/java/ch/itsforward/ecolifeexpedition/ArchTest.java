package ch.itsforward.ecolifeexpedition;

import com.tngtech.archunit.core.domain.JavaClasses;
import com.tngtech.archunit.core.importer.ClassFileImporter;
import com.tngtech.archunit.core.importer.ImportOption;
import org.junit.jupiter.api.Test;

import static com.tngtech.archunit.lang.syntax.ArchRuleDefinition.noClasses;

class ArchTest {

    @Test
    void servicesAndRepositoriesShouldNotDependOnWebLayer() {

        JavaClasses importedClasses = new ClassFileImporter()
            .withImportOption(ImportOption.Predefined.DO_NOT_INCLUDE_TESTS)
            .importPackages("ch.itsforward.ecolifeexpedition");

        noClasses()
            .that()
                .resideInAnyPackage("ch.itsforward.ecolifeexpedition.service..")
            .or()
                .resideInAnyPackage("ch.itsforward.ecolifeexpedition.repository..")
            .should().dependOnClassesThat()
                .resideInAnyPackage("..ch.itsforward.ecolifeexpedition.web..")
        .because("Services and repositories should not depend on web layer")
        .check(importedClasses);
    }
}
