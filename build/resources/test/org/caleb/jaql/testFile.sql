select * from badnameloc -- name: badNameLocation
select * from underBadNameLoc

-- name: firstQuery

select * from something;

-- name: secondQuery

select * from nothing;

-- name: jacobQuery

select * from jacob

-- name: calebQuery

select * from caleb

--name: justiceQuery

select * from justice

--name:noSpaceQuery

select * from nospace

--   name:    manySpaceQuery

select * from manyspaces

     --      name:    allSpaceQuery

select * from allspaces

-- name: queryWithComment
-- comment below meant to mix things up
-- name:

-- another comment
/* before query */
select * from queryWithComment
select * from inlineComment --here's a comment
/* this is a
multiline comment
within the query */ select * from multilineComment
/* after query */

-- name: blankQuery

-- name: spaced Name Query

select * from spacedname

-- name: endQuery

select * from endquery

-- name: templateQuery

select * from templateQuery where name = '${name}'

-- name: notAQueryButHl7ExtraUseCaseMultiline

MSH|^~\&|EPIC|EPICADT|SMS|SMSADT|199912271408|CHARRIS|ADT^${ADTTYPE}|1817457|D|2.5|
PID||0493575^^^2^ID 1|454721||DOE^JOHN^^^^|DOE^JOHN^^^^|19480203|M||B|254 MYSTREET AVE^^${Town}^OH^44123^USA||(216)123-4567|||M|NON|400003403~1129086|
NK1||${LastName}^${FirstName}^^^^|SPO||(216)123-4567||EC|||||||||||||||||||||||||||
PV1||O|168 ~219~C~PMA^^^^^^^^^||||277^ALLEN MYLASTNAME^BONNIE^^^^|||||||||| ||2688684|||||||||||||||||||||||||199912271408||||||002376853
