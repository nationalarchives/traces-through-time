**The National Archives**

**“Traces Through Time”**

**Graph Based Data Schema**

**Modelled in the**
[**CYPHER**](http://neo4j.com/developer/cypher-query-language/) **Query
Language**

**v2.1**

**June 2016**

**1. NODES**

Nodes are shown in the form “**:nodeExample”** (bold camelCase).

Node property names are in lower-case **bold**.

Variable examples of property values are in *green*.

List examples of property values are in blue.

NB. All nodes have an optional **comments** property for general notes
or comments of any kind.

**2. EDGES**

Edges (predicates) are shown in the form “[:edgeExample]” (bracketed
camelCase).

Edge properties are in red camelCase.

Edge properties are always *optional* unless otherwise stated.

NB. All edges have an optional comments property for general notes or
comments of any kind.

*Property names are generic wherever possible (ref, type, name) to avoid
proliferation of names.*

*The examples provided throughout this document come as unqualified
CYPHER statements, i.e. statements without a preliminary CREATE, MERGE
or MATCH verb.*

*This data model has also been expressed as a JSON schema:* cf.
TTTJSONschema.v2-1.json

*An illustrative JSON instance is also available for reference:* cf.
PaulMcCartney.json

PART 1: NODES
=============

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:age**

**end** {int} optional *(46)*

**name** {string} optional *(mid-forties)*

**start** {int} mandatory *(44)*

**type** {list} optional APPROX|EXACT

*Use age range (***start** *and* **end***) where applicable (otherwise*
**start** *only)*

*Use* **name** *to record the original textual description of age*

*Use* **type** *to record whether the age is exact or approximate*

**Example:** *“Harrison was only 14 when he first auditioned for The
Quarrymen in March 1958”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Harrison”})

(x:person)-[:hasAge {dateStart: 195803}]-\>(:age {start: 14})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:alias**

**name** {string} mandatory *(Longshanks, Jack the Ripper, Mata Hari,
Pelé)*

> [:hasParticle] {type: “descriptive|nobiliary|prepositional”}
> **:particle**
>
> optional; 0 or 1

**Example:** *“Paul McCartney is affectionately known to fans as Macca”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“McCartney”})

(x:person)-[:hasForeNames {order: 2}]-\>(:foreName {name: “Paul”})

(x:person)-[:hasAliases {type: “nickname”}]-\>(:alias {name: “Macca”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:award**

**name** {string} mandatory *(BA, MSc, KB, VC, Victoria Cross)*

> [:awardAliases] {type:
> “acronym|abbreviation|alternative|full|nickname”} **:award**
>
> optional; 0 or more
>
> *Use type where applicable with a suitable value*

**Example:** *“Paul McCartney, MBE (Member of the Most Excellent Order
of the British Empire)”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“McCartney”})

(x:person)-[:hasForeNames {order: 2}]-\>(:foreName {name: “Paul”})

(x:person)-[:hasAwards]-\>(:award {name: “MBE”})-[:awardAliases {type:
“full”}]-\>(:award {name: “Member of the Most Excellent Order of the
British Empire”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:context**

**name** {string} mandatory

**ref** {string} optional *(para.1, sub-sect.2, B33)*

*Use* **name** *to capture the immediate context (eg. sentence, cell or
simply 50 chars either side) in which a person is found.*

*Use* **ref** *to record an original machine-readable or system
identifier*

> [:hasDay] {type: “APPROX|EXACT”} **:day**
>
> optional; 0 or 1
>
> [:hasEndYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> [:hasMonth] {type: “APPROX|EXACT”} **:month**
>
> optional; 0 or 1
>
> [:hasStartYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> [:hasTime] {type: “APPROX|EXACT”} **:time**
>
> optional; 0 or 1
>
> [:hasYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> *Where possible this should be the date of the entry or event
> described in the immediate context (eg. sentence or cell) in
> preference to the date of the wider container **:document***
>
> [:inContainer] **:document**
>
> optional; 0 or 1

**Example:** *“In 2002, the airport in Lennon's home town was renamed
Liverpool John Lennon Airport”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(x:person)-[:inContainer]-\>(a:context {name: “In 2002, the airport in
Lennon's home town was renamed Liverpool John Lennon Airport”)

(a:context)-[:hasYear]-\>(:year {value: 2002})

(a:context)-[:inContainer]-\>(:document {URL:
“http://en.wikipedia.org/wiki/John\_Lennon\#Legacy”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:day**

**name** {string} optional *(16th)*

**value** {int} mandatory *(16)*

*Use* **name** *to record the original textual date description, if
desired*

> [:hasMonth] {type: “APPROX|EXACT”} **:month**
>
> optional; 0 or 1

**Example: “***Brian Epstein dismissed Pete Best from The Beatles on 16
August 1962”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Epstein”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Brian”})

(y:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Best”})

(y:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Pete”})

(a:organisation {name: “The Beatles”, genre: “music”, type: “group”})

(b:event {genre: “occupation”, type: “dismissal”})-[:hasDay]-\>(:day
{value: 16})-[:hasMonth]-\>(:month {value: 8})-[:hasYear]-\>(:year
{value: 1962})

(b:event)-[:hasOrganisations]-\>(a:organisation)

(y:person)-[:hasEvents]-\>(b:event)

(y:person)-[:hasRoles {dateEnd: 19620816}]-\>(a:organisation)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:document**

**filepath** {string} optional *(c:\\files\\example.txt)*

**name** {string} optional *(PROB 11/1/1)*

**quality** {list} optional high|low|medium

**ref** {string} optional *(D967088)*

**type** {list} optional metadata|page|paragraph|row|spreadsheet|webpage

**URL** {string} optional
*(discovery.nationalarchives.gov.uk/details/r/D967088)*

*Use to refer to either the parent data structure in which a person is
found (eg. webpage or spreadsheet) or a smaller, discrete unit within
such a data structure (eg. paragraph or row).*

*For an even smaller unit of contextual description (eg. sentence or
cell) see* **:context**

*For a much wider container of context (eg. website or folder) see*
**:source**

*Use* **name** *to record an original reference no.*

*Use* **quality** *to record an indication of the quality and/or
reliability of the source data*

*Use* **ref** *to record an original machine-readable or system
identifier*

*Use* **type** *to record the format of the container data structure*

*Use* **URL** *for a containing webpage (or sub-section thereof if
identified by a ‘\#’ fragment identifier)*

> [:hasDay] {type: “APPROX|EXACT”} **:day**
>
> optional; 0 or 1
>
> [:hasEndYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> [:hasMonth] {type: “APPROX|EXACT”} **:month**
>
> optional; 0 or 1
>
> [:hasStartYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> [:hasTime] {type: “APPROX|EXACT”} **:time**
>
> optional; 0 or 1
>
> [:hasYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> *Where possible this should be the date of the entry or event
> described in the immediate container **:document** (eg. paragraph or
> row) in preference to the date of the wider container **:document**
> (eg. webpage or spreadsheet). Otherwise, only date the wider container
> **:document***
>
> [:heldBy] **:organisation**
>
> optional; 0 or 1

*Use to record the repository or institution which now curates the
original document or dataset*

> [:inContainer] **:document**|**:source**
>
> optional; 0 or 1

**Example:** *“Lennon continues to be mourned throughout the world and
has been the subject of numerous memorials and tributes.” [Wikipedia,
page last modified on 26 October 2014 at 07:14]*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(x:person)-[:inContainer]-\>(a:context {name: “Lennon continues to be
mourned throughout the world and has been the subject of numerous
memorials and tributes.”)

(a:context)-[:inContainer]-\>(b:document {type: “paragraph”, URL:
“http://en.wikipedia.org/wiki/John\_Lennon\#Legacy”})

(b:document)-[:inContainer]-\>(c:document {type: “webpage”, URL:
“http://en.wikipedia.org/wiki/John\_Lennon”})

(c:document)-[:hasDay]-\>(:day {value: 26})-[:hasMonth]-\>(:month
{value: 10})-[:hasYear]-\>(:year {value: 2014})

(c:document)-[:hasTime]-\>(:time {timestamp: “2014-10-26T07:14:00”})

(c:document)-[:inContainer]-\>(:source {type: “website”, URL:
“http://en.wikipedia.org/wiki”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:event**

**genre** {list} optional
education|maritime|military|music|occupation|personal|sport|recreation

**type** {list} mandatory

baptism|birth|christening|conviction|death|discharge|dismissal|divorce|

enrolment|graduation|marriage|resignation|retirement|separation|service

*The record of a notable personal life event (eg. birth, marriage,
death, military service) or other notable organisational or world event
(eg. attack, ceremony, closure, declaration, fire, opening,
transportation)*

> [:hasDay] {type: “APPROX|EXACT”} **:day**
>
> optional; 0 or 1
>
> [:hasEndYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> [:hasMonth] {type: “APPROX|EXACT”} **:month**
>
> optional; 0 or 1
>
> [:hasOrganisations] {dateEnd: \*, dateStart: \*, precedence: 1}
> **:organisation**
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
> where known.*
>
> [:hasPlaces] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence: 1,
> type: \*} **:place**
>
> optional; 0 or more
>
> *To indicate location(s) associated with the event.*
>
> *Use date range where applicable (or* dateStart *only where not
> precise). *
>
> *Use precedence to indicate temporal order of multiple locations where
> known.*
>
> *Use type to indicate the nature of the relationship to the place
> where known,*
>
> *eg.* battlefield|home|school|university|venue|work
>
> [:hasStartYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> [:hasTime] {type: “APPROX|EXACT”} **:time**
>
> optional; 0 or 1
>
> [:hasYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1

**Example:** *“George Harrison was born in Liverpool, Lancashire,
England, on 25 February 1943”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Harrison”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “George”})

(x:person)-[:hasEvents]-\>(a:event {type: “birth”})

(a:event)-[:hasPlaces]-\>(b:place {name: “Liverpool”})

(b:place)-[:withinPlaces]-\>(c:place {name: “Lancashire”})

(c:place)-[:withinPlaces]-\>(:place {name: “England”})

(a:event)-[:hasDay]-\>(:day {value: 25})-[:hasMonth]-\>(:month {value:
2})-[:hasYear]-\>(:year {value: 1943})

**Example:** *“The Beatles last ever live concert was on the roof of
Apple HQ on 30 January 1969”*

(y:organisation {name: “The Beatles”, genre: “music”, type: “group”})

(z:organisation {name: “Apple”, genre: “music”, type: “company”})

(a:event {genre: “music”, type: “concert”})

(a:event)-[:hasOrganisations]-\>(y:organisation)

(a:event)-[:hasPlaces]-\>(:place {name: “Apple HQ”})

(a:event)-[:hasDay]-\>(:day {value: 30})-[:hasMonth]-\>(:month {value:
1})-[:hasYear]-\>(:year {value: 1969})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:familyName**

**name** {string} mandatory *(Johnson, Jonson, Jonners)*

**type** {list} optional
abbreviation|alternative|full|initial|nickname|pseudonym|variant

> [:familyNameAliases] {type:
>
> “abbreviation|alternative|full|initial|nickname|pseudonym|uncertain|variant”}
> **:familyName**
>
> optional; 0 or more
>
> *For an alternative form of **:familyName** only*
>
> *For entire forename & family name substitution, see **:alias***
>
> *Use type where applicable with a suitable value, eg.*
>
> *-* abbreviation *for shortened forms (eg. Mack. for Mackintosh)*
>
> *-* alternative *for local (intra-document) spelling variations*
>
> *-* full *for full form (eg. Spencer-Churchill) of a shortened name
> (Churchill)*
>
> *-* initial *for initial letter only (eg. H for Hillyard)*
>
> *-* nickname *for a nickname (eg. Smitty for Smith; Aggers for Agnew)*
>
> *-* pseudonym *for an adopted name (eg. Bowie for Jones; Loren for
> Scicolone)*
>
> *-* variant *for a recognised variant (eg. derived from FaNUK)*
>
> [:hasParticle] {type: “descriptive|nobiliary|prepositional”}
> **:particle**
>
> optional; 0 or 1

**Example:** *“John Lennon sometimes jokingly referred to himself as
John Lemon”*

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “John”})

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(x:person)-[:hasFamilyNames {type: “nickname”}]-\>(:familyName {name:
“Lemon”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:foreName**

**name** {string} mandatory *(William, Willie, Willy, Will, Wm, W, Bill,
Billy)*

**type** {list} optional

> abbreviation|alternative|diminutive|full|initial|nickname|pseudonym|variant
>
> [:foreNameAliases] {preference: 0, type:
> “abbreviation|alternative|diminutive|full|initial|nickname|pseudonym|variant”}
>
> **:foreName**
>
> optional; 0 or more

*For an alternative form of **:foreName** only*

*For entire forename & family name substitution, see **:alias***

*Use preference to indicate where an alias is also the ‘known as’
forename (0 by default)*

*Use type where applicable with a suitable value, eg.*

> *-* abbreviation *for shortened forms (eg. Wm for William)*
>
> *-* alternative *for local (intra-document) spelling variations*
>
> *-* diminutive *for familiar forms (eg. Jim for James; Polly for Mary;
> Rick for Richard)*
>
> *-* full *for full form of shortened names (eg. Robert for Rob)*
>
> *-* initial *for initial letter only (eg. M for Matthew)*
>
> *-* nickname *for a nickname (eg. Buster, Spike, Trigger)*
>
> *-* pseudonym *for an adopted name (eg. Sigourney for Susan)*
>
> *-* variant *for a recognised variant (eg. Mathew for Matthew)*
>
> [:hasParticle] {type: “descriptive|nobiliary|prepositional”}
> **:particle**
>
> optional; 0 or 1

**Example:** *“John Winston Lennon added Ono as a middle name by deed
poll on 22 April 1969”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “John”})

(x:person)-[:hasForeNames {order: 2}]-\>(:foreName {name: “Winston”})

(x:person)-[:hasForeNames {order: 3, dateStart: 19690422}]-\>(:foreName
{name: “Ono”})

**Example:** *“Some sources give Harold as George Harrison's middle
name; others dispute this”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Harrison”})

(x:person)-[:hasForeNames {certain: 1, order: 1}]-\>(:foreName {name:
“George”})

(x:person)-[:hasForeNames {certain: 0, order: 2}]-\>(:foreName {name:
“Harold”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:month**

**name** {string} optional *(Sept., September)*

**value** {int} mandatory *(9)*

*Use* **name** *to record the original textual date description, if
desired.*

> [:hasYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:nationality**

**name** {string} mandatory *(British, Irish, French, German)*

**ref** {id} optional *(GB, IE, FR, DE)*

**Example:** *“Ringo Starr is still British despite living predominantly
in LA and Monte Carlo”*

(x:person)-[:hasAliases {type: “pseudonym”}]-\>(:alias {name: “Ringo
Starr”})

(x:person)-[:hasPlaces {type: “home”}]-\>(:place {name: “LA”})

(x:person)-[:hasPlaces {type: “home”}]-\>(:place {name: “Monte Carlo”})

(x:person)-[:hasNationalities {order: 1}]-\>(:nationality {ref: “GB”,
name: “British”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:occupation**

**genre** {list} optional
education|finance|industry|maritime|military|music|sport|recreation

**name** {string} mandatory *(police, policeman, police constable, PC,
bobby)*

> [:hasOrganisations] {dateEnd: \*, dateStart: \*, precedence: 1}
> **:organisation**
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
> where known.*
>
> [:hasPlaces] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence: 1,
> type: \*} **:place**
>
> optional; 0 or more
>
> *To indicate location(s) associated with the event.*
>
> *Use date range where applicable (or* dateStart *only where not
> precise). *
>
> *Use precedence to indicate temporal order of multiple locations where
> known.*
>
> *Use type to indicate the nature of the relationship to the place
> where known,*
>
> *eg.* home|school|university|work
>
> [:occupationAliases] {type:
> “abbreviation|acronym|alternative|full|nickname|variant”}
>
> **:occupation**
>
> optional; 0 or more

*Use type where applicable with a suitable value, eg.*

*-* abbreviation *for shortened forms (eg. Head for Headteacher)*

> *-* acronym *for an acronym (eg. WREN for Women's Royal Naval
> Service)*

*-* alternative *for local (intra-document) spelling variations*

*-* full *for full form of shortened forms (eg. police constable for
PC)*

> *-* nickname *for a nickname (eg. bobby for policeman, navvy for
> navigator)*
>
> *-* variant *for a recognised variant (eg. police / policeman / police
> constable)*

**Example:** *“The Beatles had two principal drummers between 1960 and
1970”*

(y:organisation {name: “The Beatles”, genre: “music”, type: “group”})

(z:occupation {genre: “music”, name: “drummer”})

(z:occupation)-[:hasOrganisations {dateStart: 1960, dateEnd:
1970}]-\>(y:organisation)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:organisation**

**genre** {list} optional
education|finance|industry|maritime|military|music|sport|recreation

**name** {string} mandatory *(Co-operative Society, King’s Fusiliers,
Leeds United AFC)*

**ref** {id} optional *(66, 31)*

**type** {list} optional
bank|club|factory|group|regiment|school|team|university

> [:organisationAliases] {type:
>
> “abbreviation|acronym|alternative|full|nickname|variant”}
> **:organisation**
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
>
> [:hasPlaces] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence: 1,
> type: \*} **:place**
>
> optional; 0 or more
>
> *To indicate location(s) associated with the organisation.*
>
> *Use date range where applicable (or* dateStart *only where not
> precise). *
>
> *Use precedence to indicate temporal order of multiple locations where
> dates not known.*
>
> *Use type to indicate the nature of the relationship to the place
> where known,*
>
> *eg.* headquarters|home|origin|school|university|work

**Example: “***The Beatles, aka The Fab Four”*

(y:organisation {name: “The Beatles”, genre: “music”, type: “group”})

(y:organisation)-[:organisationAliases {type:
“nickname”}]-\>(:organisation {name: “The Fab Four”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:particle**

**name** {list} mandatory ap|bin|d’|da|de|de
la|del|della|di|do|dos|du|of|the|van|von

*To capture nobiliary particles or other descriptive/prepositional
information in names and aliases*

**Example:** *“Beatles-Platz was opened in Hamburg in 2008 by the Mayor,
Ole von Beust”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Beust”})-[:hasParticle {type: “nobiliary”}]-\>(:particle {name: “von”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Ole”})

(a:event {genre: “municipal”, type: “opening”})-[:hasPlaces]-\>(b:place
{name: “Hamburg”})

(a:event)-[:hasYear]-\>(:year {value: 2008})

(x:person)-[:hasRoles {name: “mayor”}]-\>(b:place)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:person**

**ref** {id} mandatory *(TTT000000001)*

*Property name is ‘ref’ (not ‘id’) to avoid conflict with Neo4j’s
in-built node ‘id’*

**type** {list} mandatory FEMALE|MALE|OTHER|UNKNOWN

*Values can be inferred from document or from conventional name usage;
otherwise unknown*

**timestamp** {timestamp} mandatory *(2016-06-18T00:00:00)*

*To indicate the date/time that a person was added to a TTT database or
record*

> [:inContainer] **:context**|**:document**|**:source**
>
> mandatory
>
> *To capture the context, document or data source in which a person is
> found*

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:place**

**name** {string} mandatory *(The Vicarage, King Street, Soho, London,
Devon, England, SW1A)*

**type** {list} optional
city|country|county|dwelling|location|region|street|town|village

> [:placeAliases] {type:
>
> “abbreviation|acronym|alternative|full|nickname|postcode|variant”}
> **:place**
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
> [:hasPlaces] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence: 1,
> type: \*} **:place**
>
> optional; 0 or more
>
> *To indicate another place which is wholly or partially contained by
> this place.*
>
> *Use date range where applicable (or* dateStart *only where not
> precise). *
>
> *Use precedence to indicate temporal order of multiple locations where
> dates not known.*
>
> [:withinPlaces] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence:
> 1, type: \*} **:place**
>
> optional; 0 or more
>
> *To indicate another place which wholly or partially contains this
> place.*
>
> *Use date range where applicable (or* dateStart *only where not
> precise). *
>
> *Use precedence to indicate temporal order of multiple locations where
> dates not known.*

**Example:** *“Harrison lived for his first six years in Arnold Grove,
Wavertree, Liverpool, L15 8HP”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Harrison“})

(x:person)-[:hasPlaces {type: “home”}]-\>(a:place {name: “Arnold Grove”,
type: “street”})

(a:place)-[:placeAliases {type: “postcode”}]-\>(:place {name: “L15 8HP”,
type: “street”})

(a:place)-[:withinPlaces]-\>(b:place {name: “Wavertree”, type:
“location”})

(b:place)-[:withinPlaces]-\>(:place {name: “Liverpool”, type: “city”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:reference**

**genre** {list} optional
airforce|army|maritime|military|navy|personal|police|prison

**name** {string} mandatory *(184691, E17498A, No.42)*

**type** {list} optional
catalogue|certificate|discharge|enrolment|passport|service

**Example:** *“In 1971, Lennon’s home phone no. at Tittenhurst Park was
Ascot 23022”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(x:person)-[:hasPlaces {type: “home”}]-\>(:place {name: “Tittenhurst
Park”})

(x:person)-[:hasReferences {certain: 1, dateStart: 1971}]-\>(:reference
{genre: “personal”, name: “Ascot 23022”, type: “telephone number”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:source**

**name** {string} optional

**quality** {list} optional high|low|medium

**ref** {string} optional

**type** {list} optional book|catalogue|dataset|folder|series|website

**URL** {string} optional

*To capture information regarding the wider document container (eg.
website or folder)*

*Use* **name** *to record an original reference no.*

*Use* **quality** *to record an indication of the quality and/or
reliability of the source data*

*Use* **ref** *to record an original machine-readable or system
identifier*

*Use* **type** *to record the format of the document container*

*Use* **URL** *for a containing website*

> [:hasDay] {type: “APPROX|EXACT”} **:day**
>
> optional; 0 or 1
>
> [:hasEndYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> [:hasMonth] {type: “APPROX|EXACT”} **:month**
>
> optional; 0 or 1
>
> [:hasStartYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> [:hasTime] {type: “APPROX|EXACT”} **:time**
>
> optional; 0 or 1
>
> [:hasYear] {type: “APPROX|EXACT”} **:year**
>
> optional; 0 or 1
>
> [:heldBy] **:organisation**
>
> optional; 0 or 1

*Use to record the repository or institution which curates the
catalogue, dataset, website etc.*

**Example:** *“See John Lennon’s Wikipedia entry at
http://en.wikipedia.org/wiki/John\_Lennon”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “John”})

(x:person)-[:inContainer]-\>(a:document {type: “webpage”, URL:
“http://en.wikipedia.org/wiki/John\_Lennon”})

(a:document)-[:inContainer]-\>(:source {type: “website”, URL:
“http://en.wikipedia.org/wiki”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:time**

**name** {string} optional *(11^th^ hour of the 11^th^ day of the 11^th^
month, 1918)*

**timestamp** {string} mandatory *(1918-11-11T11:00:00)*

*Use* **name** *to record the original textual date description, if
desired*

> [:hasDay] {type: “APPROX|EXACT”} **:day**
>
> optional; 0 or 1

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:title**

**name** {string} mandatory *(Mr, Ms, Rev, Sir, The elder, the younger,
Senior, Snr, II)*

*To record a customary or honorary title or an epithet.*

> [:titleAliases] {type:
> “abbreviation|acronym|alternative|full|nickname|variant”} **:title**
>
> optional; 0 or more

*Use type where applicable with a suitable value, eg.*

*-* abbreviation *for shortened forms (eg. Rev for Reverend)*

> *-* acronym *for an acronym (eg. HRH for Her Royal Highness)*

*-* alternative *for local (intra-document) spelling variations*

*-* full *for full form of shortened forms (eg. Mister for Mr)*

> *-* nickname *for a nickname (eg. Master for a boy)*
>
> *-* variant *for a recognised variant (eg. Madam / Madame)*

**Example:** *“Richard Starkey, Jr.”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Starkey”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Richard”})

(x:person)-[:hasTitles]-\>(:title {name: “Jr”})-[:titleAliases {type:
“full”}]-\>(:title {name: “Junior”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

**:year**

**name** {string} optional *(20 Eliz. I)*

**value** {int} mandatory *(1578)*

*Use* **name** *to record the original textual date description, eg.
for* [*Regnal
years*](http://en.wikipedia.org/wiki/Regnal_years_of_English_monarchs)

PART 2: EDGES
=============

Only edges on the **:person** node are listed here. Edges on subsidiary
nodes are shown in Part 1.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:aliveYear] {certain: 0|1} **:year**

optional; 0 or more

*Where DOB or DOD is not known but a ‘floruit’ date can be inferred from
the data. *

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

**Example:** *“Lennon wrote ‘Imagine’ in 1971”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(x:person)-[:aliveYear {certain: 1}]-\>(:year {value: 1971})

*Use date range if applicable, as follows:*

[:aliveYearEnd] {certain: 0|1} **:year**

optional; 0 or 1

[:aliveYearStart] {certain: 0|1} **:year**

optional; 0 or 1

**Example:** *“At school in the 1950s, McCartney was particularly
praised for his art work”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“McCartney”})

(x:person)-[:aliveYearStart {certain: 0}]-\>(:year {value: 1950})

(x:person)-[:aliveYearEnd {certain: 0}]-\>(:year {value: 1959})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:deadYear] {certain: 0|1} **:year**

optional; 0 or more

*Where DOD is not known but deceased status can be inferred from the
document. *

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

**Example:** *“Lennon was posthumously inducted into the Rock and Roll
Hall of Fame in 1994”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(x:person)-[:deadYear]-\>(:year {value: 1994})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasAge] {certain: 0|1, dateEnd: \*, dateStart: \*, type:
“APPROX|EXACT”} **:age**

optional; 0 or 1

*Where exact DOB or DOD is not known but an age can be inferred from the
document.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

**Example:** *“Harrison celebrated his 21^st^ birthday at the height of
the 60s”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Harrison”})

(x:person)-[:hasAge {dateStart: 1960, dateEnd: 1969, type:
“APPROX”}]-\>(:age {start: 21})

**Example:** *“Harrison died from cancer in 2001 whilst still only in
his fifties”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Harrison”})

(x:person)-[:hasAge {dateStart: 2001, type: “exact”}]-\>(:age {start:
50, end: 59, type: “APPROX”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasAliases] {certain: 0|1, dateEnd: \*, dateStart: \*, type: \*}
**:alias**

optional; 0 or more

*For an alias that is used in place of an **entire** name (both forename
& family name), eg. Pelé*

*For alternative individual forenames or family names, see*
**:foreName** *and* **:familyName**

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable, eg. where an alias is only used for a
specific time.*

*Use type to indicate:* acronym|alternative|nickname|pseudonym

**Example:** *“Richard Starkey, aka Ringo Starr”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Starkey”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Richard”})

(x:person)-[:hasAliases {type: “pseudonym”}]-\>(:alias {name: “Ringo
Starr”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasAwards] {certain: 0|1, dateEnd: \*, dateStart: \*, origin: \*}
**:award**

optional; 0 or more

*To indicate association with an earned qualification or honorary
award.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise)*

*Use* origin *to record the location or campaign where an individual
award was won.*

**Example:** *“Paul McCartney was made an MBE in 1965”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“McCartney”})

(x:person)-[:hasForeNames {order: 2}]-\>(:foreName {name: “Paul”})

(x:person)-[:hasAwards {dateStart: 1965}]-\>(:award {name: “MBE”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasChilds] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence: 1,
score: \*, type: \*} **:person**

optional; 0 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default. Qualify with* score *if necessary.*

*Use date range where applicable (or* dateStart *only where not
precise)*

*Use precedence to indicate temporal order of multiple children where
known.*

*Use* score *to indicate a weighting for the relationship assertion.*

*Use type to indicate:* adopted|foster|natural|step

**Example:** *“After they married, Paul McCartney legally adopted
Linda's daughter Heather”*

(a:familyName {name: “McCartney”})

(x:person)-[:hasFamilyNames {order: 1}]-\>(a:familyName)

(x:person)-[:hasForeNames {order: 2}]-\>(b:foreName {name: “Paul”})

(y:person)-[:hasFamilyNames {order: 1}]-\>(a:familyName)

(y:person)-[:hasForeNames {order: 1}]-\>(c:foreName {name: “Linda”})

(z:person)-[:hasFamilyNames {order: 1}]-\>(a:familyName)

(z:person)-[:hasForeNames {order: 1}]-\>(d:foreName {name: “Heather”})

(y:person)-[:hasChilds {type: “natural”}]-\>(z:person)

(x:person)-[:hasChilds {type: “adopted”}]-\>(z:person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasEvents] {certain: 0|1} **:event**

optional; 0 or more

*To indicate association with a notable life event, eg. birth, marriage,
death, military service*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

**Example:** *“George Harrison was born in Liverpool, Lancashire,
England, on 25 February 1943”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Harrison”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “George”})

(x:person)-[:hasEvents]-\>(a:event {type: “birth”})

(a:event)-[:hasPlaces]-\>(b:place {name: “Liverpool”})

(b:place)-[:withinPlaces]-\>(c:place {name: “Lancashire”})

(c:place)-[:withinPlaces]-\>(:place {name: “England”})

(a:event)-[:hasDay]-\>(:day {value: 25})-[:hasMonth]-\>(:month {value:
2})-[:hasYear]-\>(:year {value: 1943})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasFamilyNames] {certain: 0|1, dateEnd: \*, dateStart: \*, order: 1,
precedence: 1, preference: 1}

**:familyName**

optional; 1 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable, eg. where a change in name follows
marriage. *

*Use the mandatory order to indicate formal order of multiple names, eg.
Spencer (1) / Churchill (2)*

*Use precedence to indicate chronological sequence of family names when
there have been changes.*

*Use preference to indicate ‘known as’ name, eg. Spencer (2) / Churchill
(1)*

*Can also be an empty string where not known.*

**Example:** *“Lennon and McCartney”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(y:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“McCartney”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasForeNames] {certain: 0|1, dateEnd: \*, dateStart: \*, order: 1,
precedence: 1, preference: 1}

**:foreName**

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

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“McCartney”})

(x:person)-[:hasForeNames {order: 1, preference: 2}]-\>(:foreName {name:
“James”})

(x:person)-[:hasForeNames {order: 2, preference: 1}]-\>(:foreName {name:
“Paul”})

If it is necessary to distinguish pairs of different family names and
forenames for the same individual, it might be better to create entirely
separate **:person** nodes for each separate name pair and then link the
nodes with a [:sameAs] edge:

**Example:** *“In Japan, Sean Lennon is also known as Taro Ono”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Sean”})

(y:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Ono”})

(y:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Taro”})

(x:person)-[:sameAs {score: 100}]-\>(y:person)

Here it could be misleading to use a single **:person** node and the
[:familyNameAliases] edge on **:familyName** because “Ono” is not a
variant of “Lennon”. Likewise, it could be misleading to use the
[:foreNameAliases] edge on **:foreName** because “Taro” is not a variant
of “Sean”.

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasNationalities] {certain: 0|1, dateEnd: \*, dateStart: \*,
precedence: 1} **:nationality**

optional; 0 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable, eg. in the case of a change of
nationality*

*Use precedence to indicate temporal order of multiple nationalities
where known.*

**Example:** *“Ringo Starr is still British despite living predominantly
in LA and Monte Carlo”*

(x:person)-[:hasAliases {type: “pseudonym”}]-\>(:alias {name: “Ringo
Starr”})

(x:person)-[:hasPlaces {type: “home”}]-\>(:place {name: “LA”})

(x:person)-[:hasPlaces {type: “home”}]-\>(:place {name: “Monte Carlo”})

(x:person)-[:hasNationalities]-\>(:nationality {name: “British”, ref:
“GB”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasOccupations] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence:
1} **:occupation**

optional; 0 or more

*To indicate association with a known career, trade, job or occupation.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise). *

*Use precedence to indicate temporal order of multiple occupations where
known.*

**Example:** *“Ringo Starr has been a drummer since 1953”*

(x:person)-[:hasAliases {type: “pseudonym”}]-\>(:alias {name: “Ringo
Starr”})

(x:person)-[:hasOccupations {dateStart: 1953}]-\>(:occupation {name:
“drummer”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasParents] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence: 1,
score: \*, type: \*} **:person**

optional; 0 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default. Qualify with* score *if necessary.*

*Use date range where applicable, eg. in the case of step-parents*

*Use precedence to indicate the temporal order of multiple parents (eg.
step-parents) where known*

*Use* score *to indicate a weighting for the relationship assertion.*

*Use type to indicate:* adoptive|foster|natural|step

**Example:** *“Harrison was the youngest child of Harold Harrison and
his wife Louise (née French)”*

(a:familyName {name: “Harrison”})

(x:person {type: “male”})-[:hasFamilyNames {order: 1}]-\>(a:familyName)

(y:person {type: “male”})-[:hasFamilyNames {order: 1}]-\>(a:familyName)

(y:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Harold”})

(z:person {type: “female”})

(z:person)-[:hasFamilyNames {order: 1, precedence: 2}]-\>(a:familyName)

(z:person)-[:hasFamilyNames {order: 1, precedence: 1}]-\>(b:familyName
{name: “French”})

(z:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Louise”})

(y:person)-[:hasSpouses]-\>(z:person)

(x:person)-[:hasParents]-\>(y:person)

(x:person)-[:hasParents]-\>(z:person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasPlaces] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence: 1,
type: \*} **:place**

optional; 0 or more

*To indicate association with a place (which may in turn be associated
with another place)*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise)*

*Use precedence to indicate the temporal order of multiple place
associations when known*

*Use type to indicate the nature of the relationship to the place where
known,*

*eg.* home|origin|posting|recreation|school|training|university|work

**Example:** *“Paul McCartney lived at 57 Wimpole Street from
1963-1966”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“McCartney”})

(x:person)-[:hasForeNames {order: 2}]-\>(:foreName {name: “Paul”})

(a:place {name: “57 Wimpole Street”, type: “dwelling”})

(x:person)-[:hasPlaces {dateStart: 1963, dateEnd: 1966, type:
“home”}]-\>(a:place)

**Example:** *“Ringo Starr now lives in LA and Monte Carlo, a long way
from his native Liverpool”*

(x:person)-[:hasAliases {type: “pseudonym”}]-\>(:alias {name: “Ringo
Starr”})

(x:person)-[:hasPlaces {type: “home”}]-\>(:place {name: “LA”})

(x:person)-[:hasPlaces {type: “home”}]-\>(:place {name: “Monte Carlo”})

(x:person)-[:hasPlaces {type: “origin”}]-\>(:place {name: “Liverpool”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasReferences] {certain: 0|1, dateEnd: \*, dateStart: \*}
**:reference**

optional; 0 or more

*To indicate association with a given number, reference or code, eg. a
service number.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise)*

**Example:** *“In 1971, Lennon’s home phone no. at Tittenhurst Park was
Ascot 23022”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(x:person)-[:hasPlaces {type: “home”}]-\>(:place {name: “Tittenhurst
Park”})

(x:person)-[:hasReferences {certain: 1, dateStart: 1971}]-\>(:reference
{genre: “personal”, name: “Ascot 23022”, type: “telephone number”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasRelations] {certain: 0|1, dateEnd: \*, dateStart: \*, name: \*,
score: \*, type: \*} **:person**

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

*Other typical names:*
aunt|brother-in-law|cousin|neice|nephew|uncle|sister-in-law

*Use* score *to indicate a weighting for the relationship assertion.*

*Use type to indicate:*
adopted|adoptive|foster|half|natural|removed|step

**Example:** *“Lennon was raised by his aunt and uncle, Mimi and George
Smith”*

(x:person {type: “male”})-[:hasFamilyNames {order: 1}]-\>(a:familyName
{name: “Lennon”})

(y:person {type: “female”})-[:hasFamilyNames {order: 1}]-\>(b:familyName
{name: “Smith”})

(y:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Mimi”})

(z:person {type: “male”})-[:hasFamilyNames {order: 1}]-\>(b:familyName)

(z:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “George”})

(x:person)-[:hasRelations {name: “aunt”}]-\>(y:person)

(x:person)-[:hasRelations {name: “uncle”}]-\>(z:person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasRoles] {certain: 0|1, dateEnd: \*, dateStart: \*, name: \*,
precedence: 1}

**:event**|**:organisation**|**:person**|**:place**

optional; 0 or more

*To indicate employment or some other form of involvement (eg.
assistant, benefactor, employer, friend, member, mentor, owner, partner,
player, protégé, sponsor, supporter, volunteer) with an event,
organisation, person or place. Useful to distinguish between periods
spent with the same organisation under different roles or in different
capacities, eg. ranks in the army.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise).*

*Use* name *to capture the nature of the involvement by job title or
other descriptor, if known*

*Use precedence to indicate temporal order of multiple roles where
known.*

**Example:** *“Brian Epstein was an integral part of The Beatles between
1961 and 1967”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Epstein”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Brian”})

(y:organisation {name: “The Beatles”, genre: “music”, type: “group”})

(x:person)-[:hasRoles {dateStart: 1961, dateEnd:
1967}]-\>(y:organisation)

**Example:** *“Maharishi Mahesh Yogi was guru to The Beatles”*

(x:person)-[:hasTitles]-\>(:title {name: “Maharishi”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Mahesh”})

(x:person)-[:hasTitles]-\>(:title {name: “Yogi”})

(y:organisation {name: “The Beatles”, genre: “music”, type: “group”})

(x:person)-[:hasRoles {name: “guru”}]-\>(y:organisation)

**Example: “***May Pang became personal assistant to Lennon and Ono in
1970”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Pang“})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “May“})

(y:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon“})

(z:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Ono“})

(x:person)-[:hasRoles {dateStart: 1970, name: “personal
assistant”})-\>(y:person)

(x:person)-[:hasRoles {dateStart: 1970, name: “personal
assistant”})-\>(z:person)

**Example:** *“Ed Sullivan introduced The Beatles on stage when they
played Shea Stadium in 1965”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Sullivan”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Ed”})

(y:organisation {name: “The Beatles”, genre: “music”, type: “group”})

(a:event {genre: “music”, type: “concert”})

(y:organisation)-[hasEvents]-\>(a:event)

(a:event)-[:hasPlaces]-\>(:place {name: “Shea Stadium”})

(a:event)-[:hasYear]-\>(:year {value: 1965})

(x:person)-[:hasRoles {name: “compere”}]-\>(a:event)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasSiblings] {certain: 0|1, dateEnd: \*, dateStart: \*,
precedence: -1, score: \*, type: \*} **:person**

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

(a:familyName {name: “Harrison”})

(w:person {type: “male”})-[:hasFamilyNames {order: 1}]-\>(a:familyName)

(x:person {type: “female”})-[:hasFamilyNames {order: 1, precedence:
1}]-\>(a:familyName)

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Louise”})

(y:person {type: “male”})-[:hasFamilyNames {order: 1}]-\>(a:familyName)

(y:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Harry”})

(z:person {type: “male”})-[:hasFamilyNames {order: 1}]-\>(a:familyName)

(z:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Peter”})

(w:person)-[:hasSiblings]-\>(x:person)

(w:person)-[:hasSiblings]-\>(y:person)

(w:person)-[:hasSiblings]-\>(z:person)

(x:person)-[:hasSiblings]-\>(y:person)

(x:person)-[:hasSiblings]-\>(z:person)

(y:person)-[:hasSiblings]-\>(z:person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasSpouses] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence: 1,
score: \*, type: \*} **:person**

optional; 0 or more

*Use* certain *to indicate confidence in the assertion. True (1) by
default. Qualify with* score *if necessary.*

*Use date range where applicable (or* dateStart *only where not
precise)*

*Use precedence to indicate temporal order of multiple spouses where
known.*

*Use* score *to indicate a weighting for the relationship assertion.*

*Use type to indicate:* civil|marriage|partner

**Example:** *“George Harrison married Pattie Boyd on 21 Jan 1966 and
Olivia Arias on 2 Sep 1978”*

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “George”})

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“Harrison”})

(y:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Pattie”})

(y:person)-[:hasFamilyNames {order: 1, precedence: 1}]-\>(:familyName
{name: “Boyd”})

(z:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “Olivia”})

(z:person)-[:hasFamilyNames {order: 1, precedence: 1}]-\>(:familyName
{name: “Arias”})

(x:person)-[:hasSpouses {dateStart: 19660121, precedence: 1, type:
“marriage”}]-\>(y:person)

(x:person)-[:hasSpouses {dateStart: 19780902, precedence: 2, type:
“marriage”}]-\>(z:person)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:hasTitles] {certain: 0|1, dateEnd: \*, dateStart: \*, precedence: 1}
**:title**

optional; 0 or more

*To indicate association with a customary or honorary title.*

*May well be used in conjunction with a complementary* [:hasRoles
{name=\*}] *assertion.*

*Use* certain *to indicate confidence in the assertion. True (1) by
default.*

*Use date range where applicable (or* dateStart *only where not
precise). *

*Use precedence to indicate temporal order of multiple titles where
known.*

**Example:** *“Sir Paul McCartney”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“McCartney”})

(x:person)-[:hasForeNames {order: 2}]-\>(:foreName {name: “Paul”})

(x:person)-[:hasTitles]-\>(:title {name: “Sir”})

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:inContainer] **:context**|**:document**|**:source**

mandatory; 1

*To indicate the source text, document, metadata or electronic system in
which the person appears.*

**Example:** *“McCartney, James Paul v Lennon, John Winston Ono, Apple
Corps Ltd and Maclen Music Ltd”* [TNA Catalogue, ref. J 84/649.
<http://discovery.nationalarchives.gov.uk/details/r/C3332871>]

(x:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name:
“McCartney”})

(x:person)-[:hasForeNames {order: 1, preference: 2}]-\>(:foreName {name:
“James”})

(x:person)-[:hasForeNames {order: 2, preference: 1}]-\>(:foreName {name:
“Paul”})

(y:person)-[:hasFamilyNames {order: 1}]-\>(:familyName {name: “Lennon”})

(y:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “John”})

(y:person)-[:hasForeNames {order: 2}]-\>(:foreName {name: “Winston”})

(y:person)-[:hasForeNames {order: 3}]-\>(:foreName {name: “Ono”})

(a:organisation {name: “Apple Corps Ltd”, genre: “music”, type:
“company”})

(b:organisation {name: “Maclen Music Ltd”, genre: “music”, type:
“company”})

(c:context {name: “McCartney, James Paul v Lennon, John Winston Ono,
Apple Corps Ltd and Maclen Music Ltd”)

(x:person)-[:inContainer]-\>(c:context)\<-[:inContainer]-(y:person)

(a:organisation)-[:inContainer]-\>(c:context)\<-[:inContainer]-(b:organisation)

(d:document {name: “J 84/649”, ref: “C3332871”, type: “metadata”, URL:
“http://discovery.nationalarchives.gov.uk/details/r/C3332871”})

(d:document)-[:hasStartYear]-\>(:year {value: 1971})

(d:document)-[:hasEndYear]-\>(:year {value: 1982})

(c:context)-[:inContainer]-\>(d:document)

(e:source {name: “Discovery”, type: “catalogue”, URL:
“http://discovery.nationalarchives.gov.uk”})

(d:document)-[:inContainer]-\>(e:source)

(f:organisation {name: “The National Archives”, genre: “government”,
type: “repository”})

(f:organisation)-[:organisationAliases {type:
“acronym”}]-\>(:organisation {name: “TNA”})

(e:source)-[:heldBy]-\>(f:organisation)

\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_\_

[:sameAs] {genre: \*, id: \*, score: \*, timestamp: 2016-06-18T00:00:00,
type: \*} **:person**

optional; 0 or more

*To indicate the probability that two **:person** nodes might be the
same individual.*

*Use genre to indicate how/where the assertion was derived:*
algorithmic|documented|manual

*Use id to attach the assertion to a batch or result set stored in an
auditing database, eg. MongoDB*

*The mandatory* score *property indicates the weighting for an
individual assertion.*

*The mandatory* timestamp *property is to show when the assertion was
made.*

*Use type to indicate the algorithmic methods employed (use multiple
hyphenated if necessary) eg.*

> DL = Damerau-Levenstein, DM = Double Metaphone, J = Jaccard, JW =
> Jaro-Winkler,
>
> M = Metaphone, NYSIIS = New York State Identification and Intelligence
> System, S = Soundex

**Example:** *“The J. P. McCartney in question was none other than Paul
McCartney”*

(x:person)-[:hasFamilyNames {order: 1}]-\>(a:familyName {name:
“McCartney”})

(x:person)-[:hasForeNames {order: 1}]-\>(:foreName {name: “J”, type:
“initial”})

(x:person)-[:hasForeNames {order: 2}]-\>(:foreName {name: “P”, type:
“initial”})

(y:person)-[:hasFamilyNames {order: 1}]-\>(a:familyName)

(y:person)-[:hasForeNames {order: 2}]-\>(:foreName {name: “Paul”})

(x:person)-[:sameAs {timestamp: 2016-06-18T00:00:00, score: 100, type:
“documented”}]-\>(y:person)
