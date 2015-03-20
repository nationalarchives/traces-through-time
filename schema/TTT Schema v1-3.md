**Traces Through Time**

**Neo4j SCHEMA**

**v1.3**

**February 2015**

Nodes are in **:Bold** CamelCase, prefixed by a colon.

Node property names are in lower-case **bold**.

Variable examples of property values are in *green*.

List examples of property values are in blue.

NB. All nodes have an optional **comments** property for general notes /
comments of any kind.

Edges (predicates) are as bracketed [:UPPER\_CASE] with underscores,
prefixed by a colon.

Edge properties are in red CamelCase, suffixed by a colon.

Edge properties are always *optional* unless otherwise stated.

NB. All edges have an optional comments property for general notes /
comments of any kind.

*Property names are generic wherever possible (ref, type, name) to avoid
proliferation of names.*

*Examples provided throughout this document come as unqualified CYPHER
statements. A general illustration using JSON is also available as an
Appendix.*

*The primary* **:Person** *node is defined first, followed by all other
nodes in alphabetical order.*

**:Person**

**ref** {id} mandatory *(TTT000000001)*

*Property name is ‘ref’ (not ‘id’) to avoid conflict with Neo4j’s
in-built node ‘id’*

**type** {list} mandatory male|female|other|unknown

*Values can be inferred from document or from conventional name usage;
otherwise unknown*

**upload\_date** {timestamp} mandatory *(2014-11-11T00:00:00)*

*To indicate the date/time that a person was added to a TTT database or
record*

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_AGE] {certain: 0|1, dateStart: \*, dateEnd: \*, type:
“approx|exact”} **:Age**

optional; 0 or 1

*Where exact DOB or DOD is not known but an age can be inferred from the
document.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

**Example:** *“Harrison celebrated his 21^st^ birthday at the height of
the 60s”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Harrison”})

(x:Person)-[:HAS\_AGE {dateStart: 1960, dateEnd: 1969, type:
“approx”}]-\>(b:Age {start: 21})

**Example:** *“Harrison died from cancer in 2001 whilst still only in
his fifties”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Harrison”})

(x:Person)-[:HAS\_AGE {dateStart: 2001, type: “exact”}]-\>(b:Age {start:
50, end: 59, type: “approx”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_ALIAS] {certain: 0|1, dateStart: \*, dateEnd: \*, type: \*}
**:Alias**

optional; 0 or more

*For an alias that is used in place of an **entire** name (both forename
& family name), eg. Pelé*

*Or for an* epithet *that is used in addition to an entire name, eg.
jun., the elder, II*

*For alternative individual forenames or family names, see*
**:ForeName** *and* **:FamilyName**

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable, eg. where an alias is only used for a
specific time.*

*Use type to indicate:* acronym|alternative|epithet|nickname|pseudonym

**Example:** *“Richard Starkey, aka Ringo Starr”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Starkey”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “Richard”})

(x:Person)-[:HAS\_ALIAS {type: “pseudonym”}]-\>(c:Alias {name: “Ringo
Starr”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_AWARD] {campaign: \*, certain: 0|1, dateStart: \*, dateEnd: \*,
place: \*} **:Award**

optional; 0 or more

*To indicate association with an earned qualification or honorary
award.*

*Use* campaign *to record the military campaign in which an individual
award was won.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise)*

*Use* place *to record the location where an individual award was won.*

**Example:** *“Paul McCartney was made an MBE in 1965”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“McCartney”})

(x:Person)-[:HAS\_FORENAME {order: 2}]-\>(b:ForeName {name: “Paul”})

(x:Person)-[:HAS\_AWARD {dateStart: 1965}]-\>(c:Award {name: “MBE”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_CHILD] {certain: 0|1, dateStart: \*, dateEnd: \*, order: 1,
score: \*, type: \*} **:Person**

optional; 0 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default. Qualify with* score *if necessary.*

*Use date range where applicable (or* dateStart *only where not
precise)*

*Use precedence to indicate temporal order of multiple children where
dates not known.*

*Use* score *to indicate a weighting for the relationship assertion.*

*Use type to indicate:* adopted|foster|natural|step

**Example:** *“After they married, Paul McCartney legally adopted
Linda’s daughter Heather”*

(a:FamilyName {name: “McCartney”})

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName)

(x:Person)-[:HAS\_FORENAME {order: 2}]-\>(b:ForeName {name: “Paul”})

(y:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName)

(y:Person)-[:HAS\_FORENAME {order: 1}]-\>(c:ForeName {name: “Linda”})

(z:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName)

(z:Person)-[:HAS\_FORENAME {order: 1}]-\>(d:ForeName {name: “Heather”})

(y:Person)-[:HAS\_CHILD {type: “natural”}]-\>(z:Person)

(x:Person)-[:HAS\_CHILD {type: “adopted”}]-\>(z:Person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_EVENT] {certain: 0|1} **:Event**

optional; 0 or more

*To indicate association with a notable life event, eg. birth, marriage,
death, military service*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

**Example:** *“George Harrison was born in Liverpool, Lancashire,
England, on 25 February 1943”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Harrison”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “George”})

(x:Person)-[:HAS\_EVENT]-\>(c:Event {type: “birth”})

(c:Event)-[:HAS\_PLACE]-\>(d:Place {name: “Liverpool”})

(d:Place)-[:IS\_WITHIN]-\>(e:Place {name: “Lancashire”})

(e:Place)-[:IS\_WITHIN]-\>(f:Place {name: “England”})

(c:Event)-[:HAS\_DATE]-\>(g:Day {value: 25})-[:HAS\_DATE]-\>(h:Month
{value: 2})-[:HAS\_DATE]-\>(i:Year {value: 1943})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_FAMILYNAME] {certain: 0|1, dateStart: \*, dateEnd: \*, order: 1,
precedence: 1, preference: 1}

**:FamilyName**

optional; 1 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable, eg. where a change in name follows
marriage. *

*Use the mandatory order to indicate formal order of multiple names, eg.
Spencer (1) / Churchill (2)*

*Use precedence to indicate chronological sequence of family names when
there have been changes.*

*Use preference to indicate ‘known as’ name, eg. Churchill (1) /
Spencer-Churchill (2)*

*Can also be an empty string where not known.*

**Example:** *“Lennon and McCartney”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(y:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(b:FamilyName {name:
“McCartney”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_FORENAME] {certain: 0|1, dateStart: \*, dateEnd: \*, order: 1,
precedence: 1, preference: 1}

**:ForeName**

optional; 1 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable, eg. where a change in name.*

*Use the mandatory order to indicate formal order of multiple forenames,
eg. James (1) / Paul (2)*

*Use precedence to indicate chronological sequence of forenames when
there have been changes.*

*Use preference to indicate ‘known as’ forename, eg. James (2) / Paul
(1)*

*Can also simply be an initial, or an empty string where not known.*

**Example:** *“James Paul McCartney”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“McCartney”})

(x:Person)-[:HAS\_FORENAME {order: 1, preference: 2}]-\>(b:ForeName
{name: “James”})

(x:Person)-[:HAS\_FORENAME {order: 2, preference: 1}]-\>(c:ForeName
{name: “Paul”})

If it is necessary to distinguish pairs of different family names and
forenames for the same individual, create entirely separate **:Person**
nodes for each separate name pair and link them using a [:SAME\_AS]
assertion:

**Example:** *“In Japan, Sean Lennon is also known as Taro Ono”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “Sean”})

(y:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(c:FamilyName {name: “Ono”})

(y:Person)-[:HAS\_FORENAME {order: 1}]-\>(d:ForeName {name: “Taro”})

(x:Person)-[:SAME\_AS]-\>(y:Person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_NATIONALITY] {certain: 0|1, dateStart: \*, dateEnd: \*, order: 1}
**:Nationality**

optional; 0 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable, eg. in the case of a change of
nationality*

*Use precedence to indicate temporal order of multiple nationalities
where dates not known.*

**Example:** *“Ringo Starr is still British despite living predominantly
in LA and Monte Carlo”*

(x:Person)-[:HAS\_ALIAS {type: “pseudonym”}]-\>(a:Alias {name: “Ringo
Starr”})

(x:Person)-[:HAS\_PLACE {type: “home”}]-\>(b:Place {name: “LA”})

(x:Person)-[:HAS\_PLACE {type: “home”}]-\>(c:Place {name: “Monte
Carlo”})

(x:Person)-[:HAS\_NATIONALITY]-\>(d:Nationality {ref: “GB”, name:
“British”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_OCCUPATION] {certain: 0|1, dateStart: \*, dateEnd: \*, order: 1}
**:Occupation**

optional; 0 or more

*To indicate association with a known career, trade, job or occupation.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise). *

*Use precedence to indicate temporal order of multiple occupations where
dates not known.*

**Example:** *“Ringo Starr has been a drummer since 1953”*

(x:Person)-[:HAS\_ALIAS {type: “pseudonym”}]-\>(a:Alias {name: “Ringo
Starr”})

(x:Person)-[:HAS\_OCCUPATION {dateStart: 1953}]-\>(b:Occupation {name:
“drummer”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_ORIGIN] {certain: 0|1, dateStart: \*, dateEnd: \*} **:Place**

optional; 0 or 1

*To indicate association with a place of birth or origin, eg. for
migrants*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise)*

**Example:** *“Ringo Starr now lives in LA and Monte Carlo, a long way
from his native Liverpool”*

(x:Person)-[:HAS\_ALIAS {type: “pseudonym”}]-\>(a:Alias {name: “Ringo
Starr”})

(x:Person)-[:HAS\_PLACE {type: “home”}]-\>(b:Place {name: “LA”})

(x:Person)-[:HAS\_PLACE {type: “home”}]-\>(c:Place {name: “Monte
Carlo”})

(x:Person)-[:HAS\_ORIGIN]-\>(d:Place {name: “Liverpool”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_PARENT] {certain: 0|1, dateStart: \*, dateEnd: \*, order: 1,
score: \*, type: \*} **:Person**

optional; 0 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default. Qualify with* score *if necessary.*

*Use date range where applicable, eg. in the case of step-parents*

*Use precedence to indicate temporal order of multiple parents (eg.
step-parents) where dates not known*

*Use* score *to indicate a weighting for the relationship assertion.*

*Use type to indicate:* adoptive|foster|natural|step

**Example:** *“Harrison was the youngest child of Harold Harrison and
his wife Louise (née French)”*

(a:FamilyName {name: “Harrison”})

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName)

(y:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName)

(y:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “Harold”})

(z:Person {type: “female”})

(z:Person)-[:HAS\_FAMILYNAME {order: 1, precedence: 1}]-\>(c:FamilyName
{name: “French”})

(z:Person)-[:HAS\_FAMILYNAME {order: 1, precedence: 2}]-\>(a:FamilyName)

(z:Person)-[:HAS\_FORENAME {order: 1}]-\>(d:ForeName {name: “Louise”})

(y:Person)-[:HAS\_SPOUSE]-\>(z:Person)

(x:Person)-[:HAS\_PARENT]-\>(y:Person)

(x:Person)-[:HAS\_PARENT]-\>(z:Person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_PLACE] {certain: 0|1, dateStart: \*, dateEnd: \*, type: \*}
**:Place**

optional; 0 or more

*To indicate association with a place (which may in turn be associated
with another place)*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise)*

*Use type to indicate the nature of the relationship to the place where
known,*

*eg.* home|posting|recreation|school|training|university|work

**Example:** *“Paul McCartney lived at 57 Wimpole Street from
1963-1966”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“McCartney”})

(x:Person)-[:HAS\_FORENAME {order: 2}]-\>(b:ForeName {name: “Paul”})

(c:Place {name: “57 Wimpole Street”, type: “dwelling”})

(x:Person)-[:HAS\_PLACE {dateStart: 1963, dateEnd: 1966, type:
“home”}]-\>(c:Place)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_REFERENCE] {certain: 0|1, dateStart: \*, dateEnd: \*}
**:Reference**

optional; 0 or more

*To indicate association with a given number, reference or code, eg. a
service number*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise)*

**Example:** *“In 1971, Lennon’s home phone no. at Tittenhurst Park was
Ascot 23022”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(x:Person)-[:HAS\_REFERENCE {certain: 1, dateStart:
1971}]-\>(b:Reference {genre: “personal”, name: “Ascot 23022”, type:
“telephone”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_RELATION] {certain: 0|1, dateStart: \*, dateEnd: \*, name: \*,
score: \*, type: \*} **:Person**

optional; 0 or more

*To indicate non-immediate family relations. Especially useful where
intermediate relations (person or persons) are not known - but can still
be used in addition to those.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default. Qualify with* score *if necessary.*

*Use date range where applicable, eg. in the case of foster
grandparents*

*Use name to indicate the relationship, using the following standard
notation:*

*grand (no space or hyphen), eg.*
grandchild|granddaughter|grandfather|grandparent

*great (with hyphen), eg.*
great-aunt|great-grandchild|great-granddaughter|great-grandparent

*Other typical names:* aunt|cousin|neice|nephew|uncle

*Use* score *to indicate a weighting for the relationship assertion.*

*Use type to indicate:*
adopted|adoptive|foster|half|natural|removed|step

**Example:** *“Lennon was raised by his aunt and uncle, Mimi and George
Smith”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(b:FamilyName {name: “Smith”})

(y:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(b:FamilyName)

(y:Person)-[:HAS\_FORENAME {order: 1}]-\>(c:ForeName {name: “Mimi”})

(z:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(b:FamilyName)

(z:Person)-[:HAS\_FORENAME {order: 1}]-\>(d:ForeName {name: “George”})

(x:Person)-[:HAS\_RELATION {name: “aunt”}]-\>(y:Person)

(x:Person)-[:HAS\_RELATION {name: “uncle”}]-\>(z:Person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_ROLE] {certain: 0|1, dateStart: \*, dateEnd: \*, name: \*, order:
1}

**:Event**|**:Organisation**|**:Person**

optional; 0 or more

*To indicate employment or some other form of involvement (eg.
assistant, benefactor, employer, friend, member, mentor, owner, partner,
player, protégé, sponsor, supporter, volunteer) with an organisation or
with a person or persons. Useful to distinguish between periods spent
with the same organisation under different roles or in different
capacities, eg. ranks in the army.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise).*

*Use* name *to capture the nature of the involvement by job title or
other descriptor, if known*

*Use precedence to indicate temporal order of multiple roles where dates
not known.*

**Example:** *“Brian Epstein was an integral part of The Beatles between
1961 and 1967”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Epstein”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “Brian”})

(y:Organisation {name: “The Beatles”, genre: “music”, type: “group”})

(x:Person)-[:HAS\_ROLE {dateStart: 1961, dateEnd:
1967}]-\>(y:Organisation)

**Example:** *“Maharishi Mahesh Yogi was guru to The Beatles”*

(x:Person)-[:HAS\_TITLE]-\>(a:Title {name: “Maharishi”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “Mahesh”})

(x:Person)-[:HAS\_TITLE]-\>(c:Title {name: “Yogi”})

(y:Organisation {name: “The Beatles”, genre: “music”, type: “group”})

(x:Person)-[:HAS\_ROLE {name: “guru”}]-\>(y:Organisation)

**Example: “***May Pang became personal assistant to Lennon and Ono in
1970”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name: “Pang”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “May”})

(y:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(c:FamilyName {name:
“Lennon”})

(z:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(d:FamilyName {name: “Ono”})

(x:Person)-[:HAS\_ROLE {dateStart: 1970, name: “personal
assistant”})-\>(y:Person)

(x:Person)-[:HAS\_ROLE {dateStart: 1970, name: “personal
assistant”})-\>(z:Person)

**Example:** *“Ed Sullivan introduced The Beatles on stage when they
played Shea Stadium in 1965”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Sullivan”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “Ed”})

(y:Organisation {name: “The Beatles”, genre: “music”, type: “group”})

(c:Event {genre: “music”, type: “concert”})

(c:Event)-[:HAS\_ORGANISATION]-\>(y:Organisation)

(c:Event)-[:HAS\_PLACE]-\>(d:Place {name: “Shea Stadium”})

(c:Event)-[:HAS\_DATE]-\>(e:Year {value: 1965})

(x:Person)-[:HAS\_ROLE {name: “introducer”}]-\>(c:Event)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_SIBLING] {certain: 0|1, dateStart: \*, dateEnd: \*, order: -1,
score: \*, type: \*}

> **:Person**

optional; 0 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default. Qualify with* score *if necessary.*

*Use date range where applicable (or* dateStart *only where not
precise)*

*Use precedence to indicate relative order of siblings where -1 etc. is
younger and +1 etc. is older*

*Use* score *to indicate a weighting for the relationship assertion.*

*Use type to indicate:* adopted|half|natural|step

**Example:** *“Harrison had one sister, Louise, and two brothers, Harry
and Peter”*

(a:FamilyName {name: “Harrison”})

(w:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName)

(x:Person {type: “female”})

(x:Person)-[:HAS\_FAMILYNAME {order: 1, precedence: 1}]-\>(a:FamilyName)

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “Louise”})

(y:Person {type: “male”})

(y:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName)

(y:Person)-[:HAS\_FORENAME {order: 1}]-\>(c:ForeName {name: “Harry”})

(z:Person {type: “male”})

(z:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName)

(z:Person)-[:HAS\_FORENAME {order: 1}]-\>(d:ForeName {name: “Peter”})

(w:Person)-[:HAS\_SIBLING]-\>(x:Person)

(w:Person)-[:HAS\_SIBLING]-\>(y:Person)

(w:Person)-[:HAS\_SIBLING]-\>(z:Person)

(x:Person)-[:HAS\_SIBLING]-\>(y:Person)

(x:Person)-[:HAS\_SIBLING]-\>(z:Person)

(y:Person)-[:HAS\_SIBLING]-\>(z:Person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_SPOUSE] {certain: 0|1, dateStart: \*, dateEnd: \*, order: 1,
score: \*, type: \*} **:Person**

optional; 0 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default. Qualify with* score *if necessary.*

*Use date range where applicable (or* dateStart *only where not
precise)*

*Use precedence to indicate temporal order of multiple spouses where
dates not known.*

*Use* score *to indicate a weighting for the relationship assertion.*

*Use type to indicate:* civil|marriage|partner

**Example:** *“George Harrison married Pattie Boyd on 21 Jan 1966 and
Olivia Arias on 2 Sep 1978”*

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(a:ForeName {name: “George”})

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(b:FamilyName {name:
“Harrison”})

(y:Person)-[:HAS\_FORENAME {order: 1}]-\>(c:ForeName {name: “Pattie”})

(y:Person)-[:HAS\_FAMILYNAME {order: 1, precedence: 1}]-\>(d:FamilyName
{name: “Boyd”})

(z:Person)-[:HAS\_FORENAME {order: 1}]-\>(e:ForeName {name: “Olivia”})

(z:Person)-[:HAS\_FAMILYNAME {order: 1, precedence: 1}]-\>(f:FamilyName
{name: “Arias”})

(x:Person)-[:HAS\_SPOUSE {dateStart: 19660121, precedence: 1, type:
“marriage”}]-\>(y:Person)

(x:Person)-[:HAS\_SPOUSE {dateStart: 19780902, precedence: 2, type:
“marriage”}]-\>(z:Person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:HAS\_TITLE] {certain: 0|1, dateStart: \*, dateEnd: \*, order: 1}
**:Title**

optional; 0 or more

*To indicate association with a customary or honorary title.*

*May well be used in conjunction with a complementary* [:HAS\_ROLE
{name=\*}] *assertion.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise). *

*Use precedence to indicate temporal order of multiple titles where
dates not known.*

**Example:** *“Sir Paul McCartney”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“McCartney”})

(x:Person)-[:HAS\_FORENAME {order: 2}]-\>(b:ForeName {name: “Paul”})

(x:Person)-[:HAS\_TITLE]-\>(c:Title {name: “Sir”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:IN\_CONTAINER] **:Context**|**:Document**

mandatory; 1

*To indicate the source text or document (or metadata) in which the
person appears.*

**Example:** *“McCartney, James Paul v Lennon, John Winston Ono, Apple
Corps Ltd and Maclen Music Ltd”* [TNA Catalogue, ref. J 84/649.
<http://discovery.nationalarchives.gov.uk/details/r/C3332871>]

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“McCartney”})

(x:Person)-[:HAS\_FORENAME {order: 1, preference: 2}]-\>(b:ForeName
{name: “James”})

(x:Person)-[:HAS\_FORENAME {order: 2, preference: 1}]-\>(c:ForeName
{name: “Paul”})

(y:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(d:FamilyName {name:
“Lennon”})

(y:Person)-[:HAS\_FORENAME {order: 1}]-\>(e:ForeName {name: “John”})

(y:Person)-[:HAS\_FORENAME {order: 2}]-\>(f:ForeName {name: “Winston”})

(y:Person)-[:HAS\_FORENAME {order: 3}]-\>(g:ForeName {name: “Ono”})

(h:Organisation {name: “Apple Corps Ltd”, genre: “music”, type:
“company”})

(i:Organisation {name: “Maclen Music Ltd”, genre: “music”, type:
“company”})

(j:Context {name: “McCartney, James Paul v Lennon, John Winston Ono,
Apple Corps Ltd and Maclen Music Ltd”)

(x:Person)-[:IN\_CONTAINER]-\>(j:Context)

(y:Person)-[:IN\_CONTAINER]-\>(j:Context)

(h:Organisation)-[:IN\_CONTAINER]-\>(j:Context)

(i:Organisation)-[:IN\_CONTAINER]-\>(j:Context)

(k:Document {name: “J 84/649”, quality: “high”, ref: “C3332871”, type:
“entry”, url:
“http://discovery.nationalarchives.gov.uk/details/r/C3332871”})

(k:Document)-[:HAS\_START\_DATE]-\>(l:Year {value: 1971})

(k:Document)-[:HAS\_END\_DATE]-\>(m:Year {value: 1982})

(j:Context)-[:IN\_CONTAINER]-\>(k:Document)

(n:Source {name: “Discovery”, quality: “high”, type: “catalogue”, url:
“http://discovery.nationalarchives.gov.uk”})

(k:Document)-[:IN\_CONTAINER]-\>(n:Source)

(o:Organisation {name: “The National Archives”, genre: “government”,
type: “repository”})

(o:Organisation)-[:HAS\_ALIAS {type: “acronym”}]-\>(p:Organisation
{name: “TNA”})

(n:Source)-[:HELD\_BY]-\>(o:Organisation)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:IS\_ALIVE] {certain: 0|1} **:Year**|**:Month**|**:Day**

optional; 0 or more

*Where DOB or DOD is not known but a ‘floruit’ date can be inferred from
the data. *

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

**Example:** *“Lennon wrote ‘Imagine’ in 1971”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(x:Person)-[:IS\_ALIVE {certain: 1}]-\>(b:Year {value: 1971})

*Use date range if applicable, as follows:*

[:IS\_END\_ALIVE] {certain: 0|1} **:Year**|**:Month**|**:Day**

optional; 0 or 1

[:IS\_START\_ALIVE] {certain: 0|1} **:Year**|**:Month**|**:Day**

optional; 0 or 1

**Example:** *“At school in the 1950s, McCartney was particularly
praised for his art work”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“McCartney”})

(x:Person)-[:IS\_START\_ALIVE {certain: 0}]-\>(b:Year {value: 1950})

(x:Person)-[:IS\_END\_ALIVE {certain: 0}]-\>(b:Year {value: 1959})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:IS\_DEAD] {certain: 0|1} **:Year**|**:Month**|**:Day**

optional; 0 or more

*Where DOD is not known but deceased status can be inferred from the
document. *

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

**Example:** *“Lennon was posthumously inducted into the Rock and Roll
Hall of Fame in 1994”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(x:Person)-[:IS\_DEAD]-\>(b:Year {value: 1994})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:SAME\_AS] {genre: \*, id: \*, score: \*, timestamp:
2014-11-11T00:00:00, type: \*} **:Person**

optional; 0 or more

*To indicate the probability that two **:Person** nodes might be the
same individual.*

*Use genre to indicate how/where the assertion was derived:*
algorithmic|documented

*Use id to attach the assertion to a batch or result set stored in an
auditing database, eg. MongoDB*

*The mandatory* score *property indicates the weighting for an
individual assertion.*

*The mandatory* timestamp *property is to show when the assertion was
made.*

*Use type to indicate the algorithmic methods employed (use multiple
hyphenated if necessary) eg.*

> S = Soundex, M = Metaphone, DM = Double Metaphone, NYSIIS,
>
> J = Jaccard, JW = Jaro-Winkler, DL = Damerau-Levenstein

**Example:** *“The J. P. McCartney in question was none other than Paul
McCartney”*

(a:FamilyName {name: “McCartney”})

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName)

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “J”})

(x:Person)-[:HAS\_FORENAME {order: 2}]-\>(c:ForeName {name: “P”})

(y:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName)

(y:Person)-[:HAS\_FORENAME {order: 2}]-\>(d:ForeName {name: “Paul”})

(x:Person)-[:SAME\_AS {timestamp: 2014-11-11T00:00:00, score: 1.0, type:
“documented”}]-\>(y:Person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Age**

**start** {int} mandatory *(44)*

**end** {int} optional *(46)*

**name** {string} optional *(mid-forties)*

**type** {list} optional approx|exact

*Use age range (***start** *and* **end***) where applicable (otherwise*
**start** *only)*

*Use* **name** *to record the original textual description of age*

*Use* **type** *to record whether the age is exact or approximate*

**Example:** *“Harrison was only 14 when he first auditioned for The
Quarrymen in March 1958”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Harrison”})

(x:Person)-[:HAS\_AGE {dateStart: 195803}]-\>(b:Age {start: 14})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Alias**

**name** {string} mandatory *(Longshanks, Jack the Ripper, Mata Hari,
Pelé)*

**Example:** *“Paul McCartney is affectionately known to fans as Macca”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“McCartney”})

(x:Person)-[:HAS\_FORENAME {order: 2}]-\>(b:ForeName {name: “Paul”})

(x:Person)-[:HAS\_ALIAS {type: “nickname”}]-\>(c:Alias {name: “Macca”})

> [:HAS\_PARTICLE] {type: “descriptive|nobiliary|prepositional”}
> **:Particle**
>
> optional; 0 or 1

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Award**

**name** {string} mandatory *(BA, MSc, KB, VC, Victoria Cross)*

> [:HAS\_ALIAS] {type: “acronym|abbreviation|alternative|full|nickname”}
> **:Award**
>
> optional; 0 or more
>
> *Use type where applicable with a suitable value*

**Example:** *“Paul McCartney, MBE (Member of the Most Excellent Order
of the British Empire)”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“McCartney”})

(x:Person)-[:HAS\_FORENAME {order: 2}]-\>(b:ForeName {name: “Paul”})

(x:Person)-[:HAS\_AWARD]-\>(c:Award {name: “MBE”})

(c:Award)-[:HAS\_ALIAS {type: “full”}]-(d:Award {name: “Member of the
Most Excellent Order of the British Empire”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Context**

**name** {string} mandatory

*Use* **name** *to capture the immediate context (eg. sentence, cell or
simply 50 chars either side) in which a person is found.*

> [:IN\_CONTAINER] **:Document**
>
> optional; 0 or 1
>
> [:HAS\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1
>
> *Where possible this should be the date of the entry or event
> described in the immediate context (eg. sentence or cell) in
> preference to the date of the wider container **:*****Document**
>
> [:HAS\_END\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1
>
> [:HAS\_START\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1

**Example:** *“In 2002, the airport in Lennon’s home town was renamed
Liverpool John Lennon Airport”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(x:Person)-[:IN\_CONTAINER]-\>(b:Context {name: “In 2002, the airport in
Lennon’s home town was renamed Liverpool John Lennon Airport”)

(b:Context)-[:HAS\_DATE]-\>(c:Year {value: 2002})

(b:Context)-[:IN\_CONTAINER]-\>(d:Document {url:
“http://en.wikipedia.org/wiki/John\_Lennon\#Legacy”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Day**

**value** {int} mandatory *(16)*

**name** {string} optional *(16th)*

*Use* **name** *to record the original textual date description, if
desired*

**Example: “***Brian Epstein dismissed Pete Best from The Beatles on 16
August 1962”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Epstein”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “Brian”})

(y:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(c:FamilyName {name: “Best”})

(y:Person)-[:HAS\_FORENAME {order: 1}]-\>(d:ForeName {name: “Pete”})

(z:Organisation {name: “The Beatles”, genre: “music”, type: “group”})

(e:Event {genre: “occupation”, type: “dismissal”})

(e:Event)-[:HAS\_DATE]-\>(f:Day {value: 16})-[:HAS\_DATE]-\>(g:Month
{value: 8})-[:HAS\_DATE]-\>(h:Year {value: 1962})

(e:Event)-[:HAS\_ORGANISATION]-\>(z:Organisation)

(y:Person)-[:HAS\_EVENT]-\>(e:Event)

(y:Person)-[:HAS\_ROLE {dateEnd: 19620816}]-\>(z:Organisation)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Document**

**filepath** {string} optional *(c:\\files\\example.txt)*

**name** {string} optional *(PROB 11/1/1)*

**quality** {list} optional high|low|medium

**ref** {string} optional *(D967088)*

**type** {list} optional entry|page|paragraph|row|spreadsheet|webpage

**url** {string} optional
*(discovery.nationalarchives.gov.uk/details/r/D967088)*

*Use to refer to either the parent data structure in which a person is
found (eg. webpage or spreadsheet) or a smaller, discrete unit within
such a data structure (eg. paragraph or row).*

*For an even smaller unit of contextual description (eg. sentence or
cell) see* **:Context**

*For a much wider container of context (eg. website or folder) see*
**:Source**

*Use* **name** *to record an original reference no.*

*Use* **quality** *to record an indication of the quality and/or
reliability of the source data*

*Use* **ref** *to record an original machine-readable or system
identifier*

*Use* **type** *to record the format of the container data structure*

*Use* **url** *for a containing webpage (or sub-section thereof if
identified by a ‘\#’ fragment identifier)*

> [:IN\_CONTAINER] **:Document**|**:Source**
>
> optional; 0 or 1
>
> [:HELD\_BY] **:Organisation**
>
> optional; 0 or 1

*Use to record the repository or institution which now curates the
original document or dataset*

> [:HAS\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1
>
> *Where possible this should be the date of the entry or event
> described in the immediate container **:*****Document** *(eg.
> paragraph or row) in preference to the date of the wider container
> **:*****Document** *(eg. webpage or spreadsheet). Otherwise, only date
> the wider container **:*****Document**
>
> [:HAS\_END\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1
>
> [:HAS\_START\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1

**Example:** *“Lennon continues to be mourned throughout the world and
has been the subject of numerous memorials and tributes.” [Wikipedia,
page last modified on 26 October 2014 at 07:14]*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(x:Person)-[:IN\_CONTAINER]-\>(b:Context {name: “Lennon continues to be
mourned throughout the world and has been the subject of numerous
memorials and tributes.”)

(b:Context)-[:IN\_CONTAINER]-\>(c:Document {type: “paragraph”, url:
“http://en.wikipedia.org/wiki/John\_Lennon\#Legacy”})

(c:Document)-[:IN\_CONTAINER]-\>(d:Document {type: “webpage”, url:
“http://en.wikipedia.org/wiki/John\_Lennon”})

(d:Document)-[:HAS\_DATE]-\>(e:Day {value: 26})-[:HAS\_DATE]-\>(f:Month
{value: 10})-[:HAS\_DATE]-\>(g:Year {value: 2014})

(d:Document)-[:HAS\_DATE]-\>(h:Time {timestamp: “2014-10-26T07:14:00”})

(d:Document)-[:IN\_CONTAINER]-\>(i:Source {type: “website”, url:
“http://en.wikipedia.org/wiki”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Event**

**genre** {list} optional
education|military|music|occupation|personal|sport|recreation

**type** {list} mandatory

baptism|birth|christening|conviction|death|discharge|dismissal|divorce|

enrolment|graduation|marriage|resignation|retirement|separation|service

*The record of a notable personal life event (eg. birth, marriage,
death, military service) or other notable organisational or world event
(eg. attack, ceremony, closure, declaration, fire, opening,
transportation)*

> [:HAS\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1
>
> [:HAS\_END\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1
>
> [:HAS\_START\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1
>
> [:HAS\_ORGANISATION] {dateStart: \*, dateEnd: \*, order: 1}
> **:Organisation**
>
> optional; 0 or more
>
> *To indicate association with an organisation (eg. company) or other
> body (eg. team).*
>
> *Use date range where applicable (or* dateStart *only where not
> precise). *
>
> *Use precedence to indicate temporal order of multiple organisations
> where dates not known.*
>
> [:HAS\_PLACE] {dateStart: \*, dateEnd: \*, order: 1} **:Place**
>
> optional; 0 or more
>
> *To indicate location(s) associated with the event.*
>
> *Use date range where applicable (or* dateStart *only where not
> precise). *
>
> *Use precedence to indicate temporal order of multiple locations where
> dates not known.*

**Example:** *“George Harrison was born in Liverpool, Lancashire,
England, on 25 February 1943”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Harrison”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “George”})

(x:Person)-[:HAS\_EVENT]-\>(c:Event {type: “birth”})

(c:Event)-[:HAS\_PLACE]-\>(d:Place {name: “Liverpool”})

(d:Place)-[:IS\_WITHIN]-\>(e:Place {name: “Lancashire”})

(e:Place)-[:IS\_WITHIN]-\>(f:Place {name: “England”})

(c:Event)-[:HAS\_DATE]-\>(g:Day {value: 25})-[:HAS\_DATE]-\>(h:Month
{value: 2})-[:HAS\_DATE]-\>(i:Year {value: 1943})

**Example:** *“The Beatles last ever live concert was on the roof of
Apple HQ on 30 January 1969”*

(y:Organisation {name: “The Beatles”, genre: “music”, type: “group”})

(z:Organisation {name: “Apple”, genre: “music”, type: “company”})

(a:Event {genre: “music”, type: “concert”})

(a:Event)-[:HAS\_ORGANISATION]-\>(y:Organisation)

(a:Event)-[:HAS\_PLACE]-\>(b:Place {name: “Apple HQ”})

(a:Event)-[:HAS\_DATE]-\>(c:Day {value: 30})-[:HAS\_DATE]-\>(d:Month
{value: 1})-[:HAS\_DATE]-\>(e:Year {value: 1969})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:FamilyName**

**name** {string} mandatory *(Johnson, Jonson, Jonners)*

**type** {list} optional abbreviation|full|initial|nickname|pseudonym

> [:HAS\_ALIAS] {type:
>
> “abbreviation|alternative|full|initial|nickname|pseudonym|variant”}
> **:FamilyName**
>
> optional; 0 or more

*For an alternative form of **:FamilyName** only*

*For entire forename & family name substitution, see main **:Person**
entry.*

*Use type where applicable with a suitable value, eg.*

*-* abbreviation *for shortened forms (eg. Mack. for Mackintosh)*

*-* alternative *for local (intra-document) spelling variations*

*-* full *for full form (eg. Spencer-Churchill) of a shortened name
(Churchill)*

*-* initial *for initial letter only (eg. H for Hillyard)*

*-* nickname *for a nickname (eg. Smitty for Smith; Aggers for Agnew)*

*-* pseudonym *for an adopted name (eg. Bowie for Jones, Loren for
Scicolone)*

*-* variant *for a recognised variant (eg. derived from FaNUK)*

> [:HAS\_PARTICLE] {type: “descriptive|nobiliary|prepositional”}
> **:Particle**
>
> optional; 0 or 1

**Example:** *“John Lennon sometimes jokingly referred to himself as
John Lemon”*

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(a:ForeName {name: “John”})

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(b:FamilyName {name:
“Lennon”})

(x:Person)-[:HAS\_FAMILYNAME {type: “nickname”}]-\>(c:FamilyName {name:
“Lemon”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:ForeName**

**name** {string} mandatory *(William, Willie, Willy, Will, Wm, W, Bill,
Billy)*

**type** {list} optional
abbreviation|diminutive|full|initial|nickname|pseudonym

> [:HAS\_ALIAS] {preference: 0, type:
> “abbreviation|alternative|diminutive|full|initial|nickname|pseudonym|variant”}
> **:ForeName**
>
> optional; 0 or more

*For an alternative form of **:ForeName** only*

*For entire forename & family name substitution, see main **:Person**
entry.*

*Use preference to indicate where an alias is also the ‘known as’
forename (0 by default)*

*Use type where applicable with a suitable value, eg.*

*-* abbreviation *for shortened forms (eg. Wm for William)*

*-* alternative *for local (intra-document) spelling variations*

*-* diminutive *for familiar forms (eg. Jim for James; Polly for Mary;
Rick for Richard)*

*-* full *for full form of shortened names (eg. Robert for Rob)*

*-* initial *for initial letter only (eg. M for Matthew)*

*-* nickname *for a nickname (eg. Buster, Spike, Trigger)*

*-* pseudonym *for an adopted name (eg. Sigourney for Susan)*

*-* variant *for a recognised variant (eg. Mathew for Matthew)*

**Example:** *“John Winston Lennon added Ono as a middle name by deed
poll on 22 April 1969”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “John”})

(x:Person)-[:HAS\_FORENAME {order: 2}]-\>(c:ForeName {name: “Winston”})

(x:Person)-[:HAS\_FORENAME {order: 3, dateStart:
19690422}]-\>(d:ForeName {name: “Ono”})

**Example:** *“Some sources give Harold as George Harrison’s middle
name; others dispute this”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Harrison”})

(x:Person)-[:HAS\_FORENAME {certain: 1, order: 1}]-\>(b:ForeName {name:
“George”})

(x:Person)-[:HAS\_FORENAME {certain: 0, order: 2}]-\>(c:ForeName {name:
“Harold”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Month**

**value** {int} mandatory *(9)*

**name** {string} optional *(Sept.)*

*Use* **name** *to record the original textual date description, if
desired.*

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Nationality**

**ref** {id} optional *(GB, IE, FR, DE)*

**name** {string} mandatory *(British, Irish, French, German)*

**Example:** *“Ringo Starr is still British despite living predominantly
in LA and Monte Carlo”*

(x:Person)-[:HAS\_ALIAS {type: “pseudonym”}]-\>(a:Alias {name: “Ringo
Starr”})

(x:Person)-[:HAS\_PLACE {type: “home”}]-\>(b:Place {name: “LA”})

(x:Person)-[:HAS\_PLACE {type: “home”}]-\>(c:Place {name: “Monte
Carlo”})

(x:Person)-[:HAS\_NATIONALITY {order: 1}]-\>(d:Nationality {ref: “GB”,
name: “British”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Occupation**

**name** {string} mandatory *(police, policeman, police constable, PC,
bobby)*

**genre** {list} optional
education|finance|industry|military|music|sport|recreation

> [:HAS\_ALIAS] {type:
> “abbreviation|acronym|alternative|full|nickname|variant”}
> **:Occupation**
>
> optional; 0 or more

*Use type where applicable with a suitable value, eg.*

*-* abbreviation *for shortened forms (eg. Head for Headteacher)*

> *-* acronym *for an acronym (eg. WREN for Women’s Royal Naval
> Service)*

*-* alternative *for local (intra-document) spelling variations*

*-* full *for full form of shortened forms (eg. police constable for
PC)*

> *-* nickname *for a nickname (eg. bobby for policeman, navvy for
> navigator)*
>
> *-* variant *for a recognised variant (eg. police / policeman / police
> constable)*
>
> [:HAS\_ORGANISATION] {dateStart: \*, dateEnd: \*, order: 1}
> **:Organisation**
>
> optional; 0 or more
>
> *To indicate association with an organisation (eg. company) or other
> body (eg. team).*
>
> *Use date range where applicable (or* dateStart *only where not
> precise). *
>
> *Use precedence to indicate temporal order of multiple organisations
> where dates not known.*

**Example:** *“The Beatles had two principal drummers between 1960 and
1970”*

(y:Organisation {name: “The Beatles”, genre: “music”, type: “group”})

(z:Occupation {genre: “music”, name: “drummer”})

(z:Occupation)-[:HAS\_ORGANISATION {dateStart: 1960, dateEnd:
1970}]-\>(y:Organisation)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Organisation**

**ref** {id} optional *(66, 31)*

**name** {string} mandatory *(Co-operative Society, King’s Fusiliers,
Leeds United AFC)*

**genre** {list} optional
education|finance|industry|military|music|sport|recreation

**type** {list} optional
bank|club|factory|group|regiment|school|team|university

> [:HAS\_ALIAS] {type:
>
> “abbreviation|acronym|alternative|full|nickname|variant”}
> **:Organisation**
>
> optional; 0 or more

*Use type where applicable with a suitable value, eg.*

*-* abbreviation *for shortened forms (eg. Marks for Marks & Spencer)*

> *-* acronym *for an acronym (eg. TNA for The National Archives)*

*-* alternative *for local (intra-document) spelling variations*

*-* full *for full form of shortened forms (eg. Leeds United Football
Club for LUFC)*

> *-* nickname *for a nickname (eg. Beeb for BBC)*
>
> *-* variant *for a recognised variant (eg. fire brigade / fire
> service)*

**Example: “***The Beatles, aka The Fab Four”*

(y:Organisation {name: “The Beatles”, genre: “music”, type: “group”})

(y:Organisation)-[:HAS\_ALIAS {type: “nickname”}]-\>(z:Organisation
{name: “The Fab Four”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Particle**

**name** {list} mandatory ap|d’|de|de la|del|della|di|du|of|the|van|von

*To capture nobiliary particles or other descriptive/prepositional
information in family names and aliases*

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Place**

**name** {string} mandatory *(The Vicarage, King Street, Soho, London,
Devon, England, SW1A)*

**type** {list} optional
city|country|county|dwelling|location|region|street|town|village

> [:HAS\_ALIAS] {type:
>
> “abbreviation|acronym|alternative|full|nickname|postcode|variant”}
> **:Place**
>
> optional; 0 or more

*Use type where applicable with a suitable value, eg.*

*-* abbreviation *for shortened forms (eg. Bury for Bury St Edmunds)*

> *-* acronym *for an acronym (eg. UK for United Kingdom)*

*-* alternative *for local (intra-document) spelling variations*

*-* full *for full form of shortened forms (eg. Kingston upon Hull for
Hull)*

> *-* nickname *for a nickname (eg. Blighty for Britain)*
>
> *-* postcode *for a postcode (eg. W13 for West Ealing)*
>
> *-* variant *for a recognised variant (eg. Newcastle on Tyne /
> Newcastle upon Tyne)*
>
> [:HAS\_PLACE] **:Place**
>
> optional; 0 or more
>
> *To indicate another place which is wholly or partially contained by
> this place.*
>
> [:IS\_WITHIN] **:Place**
>
> optional; 0 or more
>
> *To indicate another place which wholly or partially contains this
> place.*

**Example:** *“Harrison lived for his first six years in Arnold Grove,
Wavertree, Liverpool, L15 8HP”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Harrison”})

(x:Person)-[:HAS\_PLACE {type: “home”}]-\>(b:Place {name: “Arnold
Grove”, type: “street”})

(b:Place)-[:HAS\_ALIAS {type: “postcode”}]-\>(c:Place {name: “L15 8HP”,
type: “street”})

(b:Place)-[:IS\_WITHIN]-\>(d:Place {name: “Wavertree”, type:
“location”})

(d:Place)-[:IS\_WITHIN]-\>(e:Place {name: “Liverpool”, type: “city”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Reference**

**name** {string} mandatory *(184691, E17498A, No.42)*

**genre** {list} optional
airforce|army|military|navy|personal|police|prison

**type** {list} optional
catalogue|certificate|discharge|enrolment|passport|service

**Example:** *“In 1971, Lennon’s home phone no. at Tittenhurst Park was
Ascot 23022”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(x:Person)-[:HAS\_PLACE {type: “home”}]-\>(b:Place {name: “Tittenhurst
Park”})

(x:Person)-[:HAS\_REFERENCE {certain: 1, dateStart:
1971}]-\>(c:Reference {genre: “personal”, name: “Ascot 23022”, type:
“telephone”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Source**

**name** {string} optional

**quality** {list} optional high|low|medium

**ref** {string} optional

**type** {list} optional book|catalogue|dataset|folder|series|website

**url** {string} optional

*To capture information regarding the wider document container (eg.
website or folder)*

*Use* **name** *to record an original reference no.*

*Use* **quality** *to record an indication of the quality and/or
reliability of the source data*

*Use* **ref** *to record an original machine-readable or system
identifier*

*Use* **type** *to record the format of the document container*

*Use* **url** *for a containing website*

> [:HAS\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1
>
> [:HAS\_END\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1
>
> [:HAS\_START\_DATE] {type: “approx|exact”}
> **:Year**|**:Month**|**:Day**|**:Time**
>
> optional; 0 or 1
>
> [:HELD\_BY] **:Organisation**
>
> optional; 0 or 1

*Use to record the repository or institution which curates the
catalogue, dataset, website etc.*

**Example:** *“See John Lennon’s Wikipedia entry at
http://en.wikipedia.org/wiki/John\_Lennon”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Lennon”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “John”})

(x:Person)-[:IN\_CONTAINER]-\>(c:Document {quality: “medium”, type:
“webpage”, url: “http://en.wikipedia.org/wiki/John\_Lennon”})

(c:Document)-[:IN\_CONTAINER]-\>(d:Source {name: “Wikipedia”, quality:
“medium”, type: “website”, url: “http://en.wikipedia.org/wiki”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Time**

**timestamp** {string} mandatory *(1918-11-11T11:00:00)*

**name** {string} optional *(11^th^ hour of the 11^th^ day of the 11^th^
month, 1918)*

*Use* **name** *to record the original textual date description, if
desired*

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Title**

**name** {string} mandatory *(Mr, Ms, Sir, The elder, the younger,
Senior, Snr)*

*To record a customary or honorary title or an epithet.*

> [:HAS\_ALIAS] {type: “acronym|abbreviation|alternative|full|nickname”}
> **:Title**
>
> optional; 0 or more
>
> *Use type where applicable with a suitable value*

**Example:** *“Richard Starkey, Jr.”*

(x:Person)-[:HAS\_FAMILYNAME {order: 1}]-\>(a:FamilyName {name:
“Starkey”})

(x:Person)-[:HAS\_FORENAME {order: 1}]-\>(b:ForeName {name: “Richard”})

(x:Person)-[:HAS\_TITLE]-\>(c:Title {name: “Jr”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:Year**

**value** {int} mandatory *(1578)*

**name** {string} optional *(20 Eliz. I)*

*Use* **name** *to record the original textual date description, eg.
for* [*Regnal
years*](http://en.wikipedia.org/wiki/Regnal_years_of_English_monarchs)
