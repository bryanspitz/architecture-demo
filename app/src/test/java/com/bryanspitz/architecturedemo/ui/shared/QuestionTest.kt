package com.bryanspitz.architecturedemo.ui.shared

import com.bryanspitz.architecturedemo.testutil.launchAndTest
import io.kotest.assertions.throwables.shouldThrow
import io.kotest.core.spec.style.BehaviorSpec
import io.kotest.matchers.booleans.shouldBeTrue
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe

private const val QUESTION = "a question"

internal class QuestionTest : BehaviorSpec({
	val question = Question<String, Boolean>()
	
	Given("no question is asked") {
		Then("the state is null") {
			question.state.value.shouldBeNull()
		}
	}
	Given("a question is asked") {
		var result: Boolean? = null
		launchAndTest({ result = question.ask(QUESTION) }) {
			
			Then("the state is equal to the question parameter") {
				question.state.value shouldBe QUESTION
			}
			
			When("the question is answered") {
				question.answer(true)
				
				Then("the result is returned") {
					result!!.shouldBeTrue()
				}
			}
			
			When("the job is cancelled before being answered") {
				cancel()
				
				Then("the state is reset to null") {
					question.state.value.shouldBeNull()
				}
				
				And("the question is answered") {
					question.answer(true)
					
					Then("ignore the answer") {
						result.shouldBeNull()
					}
				}
				
				And("another question is asked") {
					launchAndTest({ question.ask("another question") }) {
						
						Then("do not throw an exception") {}
					}
				}
			}
			
			And("another question is asked") {
				Then("throw an illegal state exception") {
					shouldThrow<IllegalStateException> {
						question.ask("another question")
					}
				}
			}
		}
	}
})