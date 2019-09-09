@echo on

rem rem requires:
rem set PATH=%PATH;C:\dev\internship\bin

call mr help
call mr signup maksim.kostormin 00000000-0000-0000-0000-000000000001

call mr signup valentina.malaya 00000000-0000-0000-0000-000000000002
call mr signin 00000000-0000-0000-0000-000000000002
call mr invite 00000000-0000-0000-0000-000000000001

call mr signin 00000000-0000-0000-0000-000000000001
call mr invites
call mr accept 00000000-0000-0000-0000-000000000002
call mr signin 00000000-0000-0000-0000-000000000001
call mr invites
call mr accept 00000000-0000-0000-0000-000000000002
call mr friends
call mr message 00000000-0000-0000-0000-000000000002 "Hello, How are u?"

call mr signin 00000000-0000-0000-0000-000000000002
call mr messages
