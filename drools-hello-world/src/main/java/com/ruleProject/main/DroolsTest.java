package com.ruleProject.main;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;

import org.drools.compiler.compiler.DroolsParserException;
import org.drools.compiler.compiler.PackageBuilder;
import org.drools.core.RuleBase;
import org.drools.core.RuleBaseFactory;
import org.drools.core.WorkingMemory;

import com.ruleProject.model.Person;

public class DroolsTest {

	public static void main(String[] args) throws DroolsParserException,
			IOException {
		DroolsTest droolsTest = new DroolsTest();
		droolsTest.executeDrools();
	}

	public void executeDrools() throws DroolsParserException, IOException {

		PackageBuilder packageBuilder = new PackageBuilder();

		String ruleFile = "/com/rule/Rules.drl";
		InputStream resourceAsStream = getClass().getResourceAsStream(ruleFile);

		Reader reader = new InputStreamReader(resourceAsStream);
		packageBuilder.addPackageFromDrl(reader);
		org.drools.core.rule.Package rulesPackage = packageBuilder.getPackage();
		RuleBase ruleBase = RuleBaseFactory.newRuleBase();
		ruleBase.addPackage(rulesPackage);

		WorkingMemory workingMemory = ruleBase.newStatefulSession();

		Person person = new Person();
		person.setFicoScore(700);

		workingMemory.insert(person);
		workingMemory.fireAllRules();

		System.out.println("The person likely to get defaulted is " + person.getDefaultChances());
	}

}
