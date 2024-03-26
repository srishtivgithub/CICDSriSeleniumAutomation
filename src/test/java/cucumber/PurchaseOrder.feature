
@tag
Feature: Purchase the order from Ecommerce Website
  I want to use this template for my feature file

 
 Background:
  Given: I landed on Ecommerce page
  
  @tag2
  Scenario Outline: Positive test for Submitting the order
    Given Logged in with username <name> and password <password>
    When I add product <productName> to cart
    And checkout <productName> and submit the order
    Then "THANKYOU FOR THE ORDER." message will be displayed on ConfirmationPage

    Examples: 
      | name                        | password        | productName  |
      | dummyemailsrishti@gmail.com | Dummyemail2@    | ZARA COAT 3 |
     
