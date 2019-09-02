---
# Feel free to add content and custom Front Matter to this file.
# To modify the layout, see https://jekyllrb.com/docs/themes/#overriding-theme-defaults

layout: home
---
# message-repository

# Software Requirements Specification (SRS)
## requirements
### functional
#### user stories (features)
- As a user I should be able to sign-in/sign-up
+ As a user I should be able send friend request
+ As a user I should be able accept friend request
+ As a user I should be able decline friend request
+ As a user I should be able to send new message
+ As a user I should be able to receive new message
+ As a user I should be able to reveal last messages in descent order by limit
- As a user I should be able to fetch all messages in descent order
- As a user I should be able to see list of all executable commands 

### non-functional (TODO)
#### should be executable from shell
#### should be platform independent
#### should have auth. mechanism

Home work 1: complete full list of user stories
- should find last number of messages in DESC order
- should find all messages in DESC order since beginning
- should find last saved messages by given limit
- should update message by given id

Home work 2: Transform all user-stories into JUnit Jupiter 5 BDD styled tests
