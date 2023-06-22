# See
# https://github.com/intuit/karate#syntax-guide
# for how to write feature scenarios
Feature: As an api user I want to retrieve hottest repo in github last week

  Scenario: Get all the cat facts
    Given url microserviceUrl
    And path '/hottestRepo'
    When method GET
    Then status 200
    And match header Content-Type contains 'application/json'
    * param myParam = 1
    # Given request { numberOfRepo: 1}
    # see https://github.com/intuit/karate#schema-validation
    # Define the required schema
    * def repoSchema = { html_url : '#string', watchers_count : '#number', language: '#string', description: '#string', name: '#string'}
    # The response should have an array of Repository objects
    And match response == '#[] repoSchema'
