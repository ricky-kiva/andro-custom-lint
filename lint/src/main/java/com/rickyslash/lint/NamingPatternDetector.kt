package com.rickyslash.lint

import com.android.tools.lint.client.api.UElementHandler
import com.android.tools.lint.detector.api.Category
import com.android.tools.lint.detector.api.Detector
import com.android.tools.lint.detector.api.Implementation
import com.android.tools.lint.detector.api.Issue
import com.android.tools.lint.detector.api.JavaContext
import com.android.tools.lint.detector.api.Scope
import com.android.tools.lint.detector.api.Severity
import org.jetbrains.uast.UClass

@Suppress("UnstableApiUsage")
class NamingPatternDetector : Detector(), Detector.UastScanner {

    // specify types of code that this detector should inspect. UClass represents Java classes
    override fun getApplicableUastTypes() = listOf(UClass::class.java)

    //
    override fun createUastHandler(context: JavaContext): UElementHandler? {
        return object : UElementHandler() { // UElementHandler to handle specific elements within code (UClass in this case)
            override fun visitClass(node: UClass) { // visitClass called when code analyzer encounters Java class (UClass)
                if (node.name?.isDefinedCamelCase() == false) {
                    context.report( // report issue to the lint tool
                        ISSUE_NAMING_PATTERN,
                        node, // instance of the UClass (the Java file)
                        context.getNameLocation(node), // specify the location of the issue within the code
                        "Class naming must use CamelCase"
                    )
                }
            }
        }
    }

    private fun String.isDefinedCamelCase(): Boolean {
        val charArray = toCharArray() // get CharArray containing characters of this string
        return charArray.mapIndexed { index, c ->
            c to charArray.getOrNull(index + 1) // make a pair of current char & the next char (index + 1)
        }.none { // return true if if none of the elements match given condition
            it.first.isUpperCase() && it.second?.isUpperCase() ?: false // if the pair in mapped char is both uppercase, return false
        }
    }

    companion object {
        val ISSUE_NAMING_PATTERN: Issue = Issue.create( // instantiate issue that can be reported
            id = "NamingPattern",
            briefDescription = "Class naming need to be done using CamelCase",
            explanation = """
                Write class name using CamelCase.
                See examples on: https://google.github.io/styleguide/javaguide.html#s5.3-camel-case 
            """.trimIndent(),
            category = Category.CORRECTNESS,
            priority = 6,
            severity = Severity.WARNING,
            implementation = Implementation(
                NamingPatternDetector::class.java,
                Scope.JAVA_FILE_SCOPE
            )
        )
    }
}