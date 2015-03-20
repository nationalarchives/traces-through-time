package nl.liacs.link;

import java.util.ArrayList;
import java.util.List;

import static nl.liacs.link.util.StringUtils.getTimeStamp;

/**
 * Convenience class that transforms an Occurrence into 
 * the predefined TNA json schema format.
 *
 * Each input attribute defined in OccurrenceBase is transformed
 * to its corresponding schema property
 *
 * Person's Constructor invokes the appropriate functions that 
 * fill the value for the properties.
 * 
 * It includes two sets of classes :
 * 1st set : It includes classes/properties and functions that are meaningful for Leiden's data
 * 			 Their functions are tested, if specified otherwise.
 * 2nd set : It includes classes/properties described in the schema but are not used or tested 
 * 			 by Leiden's team. It includes basic constructors for each class.
 * 
 * @author Benjamin
 * @author Kleanthi
 */
final public class Person {
    /* METADATA */

    private String Ref;  // REQUIRED: TTT unique id
    final private Gender Type = Gender.unknown;                    // REQUIRED: gender: [female, male, other, unknown]
    final private String UploadDate = getTimeStamp();              // REQUIRED: 2014-11-11T00:00:00

    /* UNARY */
    private HasFamilyName[] HasFamilyName;
    private HasForeName[] HasForeName;
    private HasOrigin[] HasOrigin;
    private HasRole[] HasRole;
    private HasTitle[] HasTitle;
    private HasOccupation[] HasOccupation;
    private SentenceContainer InContainer;
    
    /* RELATIONS */
    
    private List<SameAs> SameAs = new ArrayList<>();
    private List<HasParent> HasParent = null;
    
    public Person(Occurrence oc) {
        if (oc == null) {
            throw new NullPointerException("Occurrence cannot be null");
        }
        
        //Leiden's data includes unique values for each attribute/property
        this.Ref = oc.getString("id");
        setTitle(oc.getString("title"));
        setForeName(oc.getString("forename"));
        setFamilyName(oc.getString("surname"),oc.getString("article"));
        setRole(oc.getString("role"));
        setOrigin(oc.getString("provenance"));
        
        if(!oc.getString("childOf").equals("") )
        {
        	if(this.HasParent == null)
        		this.HasParent = new ArrayList<>();
        	addHasParent(oc.getString("childOf"));
        }
        
        InContainer = new SentenceContainer(oc.getString("origOccurrence"));
        setSentenceId(InContainer,oc.getString("sentenceId"));
        setSectionId(InContainer,oc.getString("sectionId"));
        setEndDate(InContainer,oc.getString("appearanceDate"));
        setStartDate(InContainer,oc.getString("appearanceDate"));
        setFileId(InContainer,oc.getString("fileId"));
    }

    

	/**
     * Sets the family name of a person.
     *
     * @param name the family name to set.
     */
    public void setFamilyName(String name, String article) {
        if (!name.equals("")) {
        	this.HasFamilyName = new HasFamilyName[1];
        	if(!article.equals(""))
                this.HasFamilyName[0] = new HasFamilyName(name,1, article);
        	else
                this.HasFamilyName[0] = new HasFamilyName(name,1);
        	
            
        }
    }

    /**
     * Sets multiple family names of a person at once.
     *
     * @param names array of family names associated with the person.
     */
    public void setFamilyNames(String[] names, String[] article) {
        this.HasFamilyName = new HasFamilyName[names.length];
        for (int i = 0; i < names.length; i++) {
        	if(!article[i].equals(""))
        		this.HasFamilyName[i] = new HasFamilyName(names[i],i,article[i]);
        	else
        		this.HasFamilyName[i] = new HasFamilyName(names[i],i);
        }
    }

    /**
     * Sets the forename of a person.
     *
     * @param name the forename to set.
     */
    public void setForeName(String name) {
        if (!name.equals("")) {
            this.HasForeName = new HasForeName[1];
            this.HasForeName[0] = new HasForeName(name,1);
        }
    }

    /**
     * Sets multiple forenames of a person at once.
     *
     * @param names array of forenames associated with the person.
     */
    public void setForeNames(String[] names) {
        this.HasForeName = new HasForeName[names.length];
        for (int i = 0; i < names.length; i++) {
            this.HasForeName[i] = new HasForeName(names[i],i);
        }
    }

    /**
     * Sets the Origin associated with a person.
     *
     * @param Origin the place to set.
     */
    public void setOrigin(String Origin) {
        if (!Origin.equals("")) {
            this.HasOrigin = new HasOrigin[1];
            this.HasOrigin[0] = new HasOrigin(Origin);
        }
    }

    /**
     * Sets multiple Origins associated with a person at once.
     *
     * @param Origins the Origins associated with the person.
     */
    public void setOrigin(String[] Origins) {
        this.HasOrigin = new HasOrigin[Origins.length];
        for (int i = 0; i < Origins.length; i++) {
            this.HasOrigin[i] = new HasOrigin(Origins[i]);
        }
    }

    /**
     * Sets the role of a person.
     *
     * @param role the roles associated with the person.
     */
    public void setRole(String role) {
        if (!role.equals("")) {
            this.HasRole = new HasRole[1];
            this.HasRole[0] = new HasRole(role);
        }
    }

    /**
     * Sets multiple roles of a person at once.
     *
     * @param roles the roles of a person.
     */
    public void setRoles(String[] roles) {
        this.HasRole = new HasRole[roles.length];
        for (int i = 0; i < roles.length; i++) {
            this.HasRole[i] = new HasRole(roles[i]);
        }
    }

    /**
     * Sets the title of a person.
     *
     * @param title the title of the person.
     */
    public void setTitle(String title) {
        if (!title.equals("")) {
            this.HasTitle = new HasTitle[1];
            this.HasTitle[0] = new HasTitle(title);
        }
    }

    /**
     * Sets multiple title of a person at once.
     *
     * @param titles the titles of a person.
     */
    public void setTitles(String[] titles) {
        this.HasTitle = new HasTitle[titles.length];
        for (int i = 0; i < titles.length; i++) {
            this.HasTitle[i] = new HasTitle(titles[i]);
        }
    }
    /**
     * Sets the occupation of a person.
     *
     * @param occupation the occupation associated with the person.
     */
    public void setOccupation(String Occupation) {
        if (!Occupation.equals("")) {
            this.HasOccupation = new HasOccupation[1];
            this.HasOccupation[0] = new HasOccupation(Occupation);
        }
    }

    /**
     * Sets multiple occupations of a person at once.
     *
     * @param occupations the occupations of a person.
     */
    public void setOccupations(String[] Occupation) {
        this.HasOccupation = new HasOccupation[Occupation.length];
        for (int i = 0; i < Occupation.length; i++) {
            this.HasOccupation[i] = new HasOccupation(Occupation[i]);
        }
    }
    
    
    /**
     * Adds an SameAs relation with corresponding confidence value to the
     * Person. NOTE: since these lists must be built incrementally, an ArrayList
     * is used, where other fields simply use an primitive array.
     *
     * @param p the Person which is considered the same (supported by some
     * confidence value).
     * @param conf the confidence score associated with this relation.
     */
    public void addSameAs(Person p, float conf) {
        this.SameAs.add(new SameAs(p.Ref, conf));
    }
    
    /**
     * Adds a HasParent relation  to the Person. 
     * @param p the Person which is considered as the father (supported by some
     * confidence value).
     * 
     */
    public void addHasParent(String Ref) {
    	this.HasParent.add(new HasParent(Ref));
  
    }
    /**
     * Sets sentenceId of a Person.
     *
     * @param InContainer the SentenceContainer object.
     * @param sectionId the Id of the section where the Person was found
     */
    public void setSentenceId(SentenceContainer InContainer,String sentenceId) {
        if(!sentenceId.equals("")){
        	InContainer.setSentenceId(sentenceId);
        }
    }
   
    
    /**
     * Sets sectionId of a Person.
     *
     * @param InContainer the SentenceContainer object.
     * @param sectionId the Id of the section where the Person was found
     */
    public void setSectionId(SentenceContainer InContainer,String sectionId) {
        if(!sectionId.equals("")){
        	InContainer.setSectionId(sectionId);
        }
    }
    /**
     * Sets fileId of a Person.
     *
     * @param InContainer the SentenceContainer object.
     * @param sectionId the Id of the file where the Person was found
     */
    public void setFileId(SentenceContainer InContainer,String fileId) {
        if(!fileId.equals("")){
        	InContainer.setFileId(fileId);
        }
    }
    
    /**
     * Sets End Date of a Person appearing in text.
     *
     * @param InContainer the SentenceContainer object.
     * @param appearanceDate the date of Person last appearance
     */
    public void setEndDate(SentenceContainer InContainer,String appearanceDate) {
        if(!appearanceDate.equals("")){
        	InContainer.setHasEndDate(appearanceDate);
        }
    }
    
    /**
     * Sets Start Date of a Person appearing in text.
     *
     * @param InContainer the SentenceContainer object.
     * @param appearanceDate the date of Person first appearance
     */
    public void setStartDate(SentenceContainer InContainer,String appearanceDate) {
        if(!appearanceDate.equals("")){
        	InContainer.setHasStartDate(appearanceDate);
        }
    }
    
}

/* Schema Properties into Classes */
/**
 * An array of HasForeName is used to store all surnames of a Person in a
 * record. The constructor enforces the required field
 * HasFamilyName.FamilyName.Name to be filled.
 */
class HasFamilyName {

    private FamilyName FamilyName; //REQUIRED - person's family name
    private boolean Certain; //certainty of person's possession of a family name
    private Integer DateEnd; //end of person's possession of a family name
    private Integer DateStart; //start of person's possession of a family name
    private String Comments; //general notes / comments
    private Integer Order; //REQUIRED - formal order of person's multiple family names
    private Integer Precedence; //chronological sequence of person's possession of different family names
    private Integer Preference; //preference of person's usage of a family name
    
    /*Constructors*/
    //basic constructor
    HasFamilyName(String name, Integer Order) {
        this.FamilyName = new FamilyName(name);
        setOrder(Order);
    }
    //basic constructor including particle
    HasFamilyName(String name, Integer Order, String particleName) {
        this.FamilyName = new FamilyName(name, particleName);
        setOrder(Order);
    }
    //constructor including particle and alias
    //this constructor is not being called/tested
    HasFamilyName(String name, Integer Order, String[] HasAlias) {
        this.FamilyName = new FamilyName(name, HasAlias);
        setOrder(Order);
    }
    //constructor including particle and alias
    //this constructor is not being called/tested
    HasFamilyName(String name, Integer Order, String particleName, String[] HasAlias) {
        this.FamilyName = new FamilyName(name, particleName, HasAlias);
        setOrder(Order);
    }
    //complete constructor - not being called
    HasFamilyName(String name, boolean certain, Integer DateStart, Integer DateEnd, String Comments,
            Integer Order, Integer Precedence, Integer Preference, String[] Alias) {
        this.FamilyName = new FamilyName(name, Alias);
        setCertain(certain);
        setStart(DateStart);
        setEnd(DateEnd);
        setComments(Comments);
        setOrder(Order);
        setPrecedence(Precedence);
        setPreference(Preference);
    }
    /* Setters */
    private void setCertain(boolean certain){
        this.Certain = certain;
    }
    private void setComments(String Comments){
        this.Comments = Comments;
    }
    private void setStart(Integer start){
        if(start >= 1066 && start <= 20151231)
            this.DateStart = start;
        else
            this.DateStart = -1;
    }
    
    private void setEnd(Integer end){
        if(end >= 1066 && end <= 20151231)
            this.DateEnd = end;
        else
            this.DateEnd = -1;
    }
    private void setOrder(Integer order){
        if(order >= 1 && order <= 5)
            this.Order = order;
        else
            this.Order = -1;
    }
    private void setPrecedence(Integer Precedence){
        if(Precedence >= 1 && Precedence <= 5)
            this.Precedence = Precedence;
        else
            this.Precedence = -1;
    }
    private void setPreference(Integer Preference){
        if(Preference >= 1 && Preference <= 5)
            this.Preference = Preference;
        else
            this.Preference = -1;
    }
    static class FamilyName {
        private String Comments; //general notes / comments",
        private String Name;  // REQUIRED - name of person's family name
        //property Type not included
        private HasAlias[] HasAlias; //person's family name has alias(es)
        private HasParticle HasParticle; //article before family name
        
        
        /*Constructors*/
        //basic constructor
        FamilyName(String Name) {
            this.Name = Name;
        }
        //basic constructor with particle
        FamilyName(String Name, String particleName) {
            this.Name = Name;
            this.HasParticle = new HasParticle(particleName);
        }
        
        //constructor including FamilyName and Alias
        //this constructor is not being called/tested
        FamilyName(String Name, String[] Alias) {
            this.Name = Name;
            this.HasAlias = new HasAlias[Alias.length];
            for (int i = 0; i < Alias.length; i++) {
                this.HasAlias[i] = new HasAlias(Alias[i]);
            }
        }
        
        //constructor including FamilyName, Alias and particle
        //this constructor is not being called/tested 
        FamilyName(String Name, String particleName, String[] Alias) {
            this.Name = Name;
            this.HasParticle = new HasParticle(particleName);
            this.HasAlias = new HasAlias[Alias.length];
            for (int i = 0; i < Alias.length; i++) {
                this.HasAlias[i] = new HasAlias(Alias[i]);
            }
        }
        //complete constructor - not being called/tested
        FamilyName(String Name, String particleName, String Comments, String[] Alias) {
            this.Name = Name;
            setComments(Comments);
            this.HasParticle = new HasParticle(particleName);
            this.HasAlias = new HasAlias[Alias.length];
            for (int i = 0; i < Alias.length; i++) {
                this.HasAlias[i] = new HasAlias(Alias[i]);
            }
            
        }
        
        /* Setters */
        
        private void setComments(String Comments){
            this.Comments = Comments;
        }
        /*Alias class for Family Name*/
        static class HasAlias { // no required fields
            private String Comments; //person's family name has alias(es
            private FamilyName FamilyName;
            //property Type not included
            //basic constructor - not required
            HasAlias(String Name){ 	
            	this.FamilyName = new FamilyName(Name);
            }
            //complete constructor - not called/tested
            HasAlias(String Name, String Comments){
            	this.FamilyName = new FamilyName(Name);
            	setComments(Comments);
            }
            
            /*Setters*/
            
            private void setComments(String Comments){
                this.Comments = Comments;
            }
        }    
        
        
    }
}
/**
 * An object of Particle is created by the HasForename class to store the particle of a name associated with a Person in
 * a record. The constructor enforces the required field HasParticle.Particle.Name to be filled.
 */
class HasParticle{
    private String Comments; //general notes / comments
    private Particle Particle; //nobiliary/descriptive/prepositional particle
    
    //basic constructor with required fields
    HasParticle(String ParticleName){
        this.Particle = new Particle(ParticleName);
    }
    /*Particle class*/
    static class Particle{
        private String Comments; //general notes / comments
        private ParticleType Name; //REQUIRED - name of particle
        
        //basic constructor with required fields
        Particle(String ParticleName){
            setParticleType(ParticleName);
        }
        //complete constructor - not called
        Particle(String ParticleName, String Comments){
            setParticleType(ParticleName);
            setComments(Comments);
        }
       
        enum ParticleType{
            ap,de,del,della,di,du, of,the,van,von,
        }
        /*Setters*/
        private final void setParticleType(String type){
            switch (type){
            case "ap" : this.Name = ParticleType.ap;  break;
            case "de"  : this.Name = ParticleType.de;   break;
            case "del"  : this.Name = ParticleType.del;   break;
            case "della"  : this.Name = ParticleType.della;   break;
            case "di"  : this.Name = ParticleType.di;   break;
            case "du"  : this.Name = ParticleType.du;   break;
            case "of"  : this.Name = ParticleType.of;   break;
            case "the"  : this.Name = ParticleType.the;   break;
            case "van"  : this.Name = ParticleType.van;   break;
            case "von"  : this.Name = ParticleType.von;   break;
            
            }
        }
        private void setComments(String Comments){
            this.Comments = Comments;
        }
    }    
    
}
/**
 * An array of HasForeName is used to store all forenames of a Person in a
 * record. The constructor enforces the required field HasForename.ForeName.Name
 * to be filled.
 */
class HasForeName {

    private ForeName ForeName; //person's forenames
    private boolean Certain; //certainty of person's possession of a forename
    private Integer DateEnd; //end of person's possession of a forename
    private Integer DateStart; //start of person's possession of a forename
    private String Comments; //general notes / comments
    private Integer Order;//REQUIRED - formal order of person's multiple forenames"
    private Integer Precedence; //chronological sequence of person's possession of different forenames
    private Integer Preference; //preference of person's usage of a forename
    
    /*Constructors*/
    //basic constructor
    HasForeName(String name, Integer Order) {
        this.ForeName = new ForeName(name);
        setOrder(Order);
    }
    //basic constructor with alias
    HasForeName(String name, Integer Order, String[] HasAlias) {
        this.ForeName = new ForeName(name, HasAlias);
        setOrder(Order);
    }
    //complete constructor - not called/tested
    HasForeName(String name, boolean certain, Integer DateStart, Integer DateEnd, String Comments,
            Integer Order, Integer Precedence, Integer Preference, String[] HasAlias) {
        this.ForeName = new ForeName(name, HasAlias);
        setCertain(certain);
        setStart(DateStart);
        setEnd(DateEnd);
        setComments(Comments);
        setOrder(Order);
        setPrecedence(Precedence);
        setPreference(Preference);
    }
    /* Setters */
    private void setCertain(boolean certain){
        this.Certain = certain;
    }
    private void setComments(String Comments){
        this.Comments = Comments;
    }
    private void setStart(Integer start){
        if(start >= 1066 && start <= 20151231)
            this.DateStart = start;
        else
            this.DateStart = -1;
    }
    
    private void setEnd(Integer end){
        if(end >= 1066 && end <= 20151231)
            this.DateEnd = end;
        else
            this.DateEnd = -1;
    }
    private void setOrder(Integer order){
        if(order >= 1 && order <= 5)
            this.Order = order;
        else
            this.Order = -1;
    }
    private void setPrecedence(Integer Precedence){
        if(Precedence >= 1 && Precedence <= 5)
            this.Precedence = Precedence;
        else
            this.Precedence = -1;
    }
    private void setPreference(Integer Preference){
        if(Preference >= 1 && Preference <= 5)
            this.Preference = Preference;
        else
            this.Preference = -1;
    }
    /*Forename Class*/
    static class ForeName { 
        private String Comments; //general notes / comments
        private String Name;  // REQUIRED - forename      
        private HasAlias[] HasAlias; //forename has alias(es)
        //property Type not included
        
        //basic constructor
        ForeName(String Name) {
            this.Name = Name;
        }
        //basic constructor with Alias - not called/tested
        ForeName(String Name, String[] Alias) {
            this.Name = Name;
            this.HasAlias = new HasAlias[Alias.length];
            for (int i = 0; i < Alias.length; i++) {
                this.HasAlias[i] = new HasAlias(Alias[i]);
            }
        }
        //complete constructor - not called/tested
        ForeName(String Name, String Comments, String[] Alias) {
            this.Name = Name;
            setComments(Comments);
            this.HasAlias = new HasAlias[Alias.length];
            for (int i = 0; i < Alias.length; i++) {
                this.HasAlias[i] = new HasAlias(Alias[i]);
            }
            
        }
        /*Setters*/
        
        private void setComments(String Comments){
            this.Comments = Comments;
        }
        /*Alias class for ForeName*/
        static class HasAlias { //no required fields
            private String Comments; //general notes / comments
            private ForeName ForeName;
            //property Type not included
           //basic constructor 
            HasAlias(String Name){
            	this.ForeName = new ForeName(Name);
            }
            //complete constructor - not called/tested
            HasAlias(String Name, String Comments){
            	this.ForeName = new ForeName(Name);
            	setComments(Comments);
            }
            /* Setters */
            private void setComments(String Comments){
                this.Comments = Comments;
            }

        }    
        
        
    }
}
/**
 * An array of HasOccupation is used to store all occupations of a Person in a
 * record. The constructor enforces the required field
 * HasOccupation.Occupation.Name to be filled.
 */
class HasOccupation{
	private boolean Certain; //certainty of age identification
    private Integer DateEnd; //end of dates with occupation
    private Integer DateStart; //start of date(s) with occupation
    private String Comments; //general notes / comments
    private Integer Precedence; //sequence of occupation
    private Occupation Occupation; //person's occupation
    
    /*Constructors*/
    //basic constructor
    HasOccupation(String Name){
    	this.Occupation = new Occupation(Name);
    }
    //constructor with Alias - not called/tested
    HasOccupation(String Name, String[] HasAlias){
    	this.Occupation = new Occupation(Name, HasAlias);
    }
    //complete constructor - not called/tested
    HasOccupation(String Name,boolean certain, Integer DateStart, Integer DateEnd, String Comments,
            Integer Precedence, String[] HasAlias){
    	this.Occupation = new Occupation(Name, HasAlias);
    	setCertain(certain);
        setStart(DateStart);
        setEnd(DateEnd);
        setComments(Comments);
        setPrecedence(Precedence);
    }
    /* Setters */
    private void setCertain(boolean certain){
        this.Certain = certain;
    }
    private void setComments(String Comments){
        this.Comments = Comments;
    }
    private void setStart(Integer start){
        if(start >= 1066 && start <= 20151231)
            this.DateStart = start;
        else
            this.DateStart = -1;
    }
    
    private void setEnd(Integer end){
        if(end >= 1066 && end <= 20151231)
            this.DateEnd = end;
        else
            this.DateEnd = -1;
    }
    
    private void setPrecedence(Integer Precedence){
        if(Precedence >= 1 && Precedence <= 25)
            this.Precedence = Precedence;
        else
            this.Precedence = -1;
    }
    
    /*Occupation class*/
    static class Occupation{ 
    	private String Comments; //general notes / comments 
    	private String Name; // REQUIRED - name of occupation
    	private HasAlias[] HasAlias; //occupation has alias(es)
        //property Genre not not included
    	/* Constructors */
    	//basic constructor
    	Occupation(String Name){
    		this.Name = Name;
    	}
    	//basic constructor with Alias - not called/tested
    	Occupation(String Name, String[] HasAlias){
    		this.Name = Name;
    		this.HasAlias = new HasAlias[HasAlias.length];
            for (int i = 0; i < HasAlias.length; i++) {
                this.HasAlias[i] = new HasAlias(HasAlias[i]);
            }
    	}
    	//complete constructor with Alias - not called/tested
    	Occupation(String Name, String Comments,  String[] HasAlias){
    		this.Name = Name;
    		setComments(Comments);
    		this.HasAlias = new HasAlias[HasAlias.length];
            for (int i = 0; i < HasAlias.length; i++) {
                this.HasAlias[i] = new HasAlias(HasAlias[i]);
            }
    	}
    	
    	/* Setters */
    	private void setComments(String Comments){
            this.Comments = Comments;
        }
    	
    	/*Alias class for Occupation*/
    	static class HasAlias{// no required fields
    		private String Comments; //general notes / comments
    		private Occupation Occupation;
            //property Type not included
    		/*Constructors*/
    		//basic constructor with required field
    		HasAlias(String Name){
    			this.Occupation = new Occupation(Name);
    		}
    		//complete constructor - not called/tested
    		HasAlias( String Comments, String Name){
    			setComments(Comments);
    			this.Occupation = new Occupation(Name);
    		}
    		
    		/* Setters */
    		private void setComments(String Comments){
    	        this.Comments = Comments;
    	    }
    		
    		
    	}
    }
}
/**
 * An array of HasRole is used to store all roles of a Person in a record. The
 * constructor enforces the required field HasRole.Name to be filled.
 */
class HasRole { //no required fields
	
	private String Name; //description or nature of the role
    private boolean Certain; //certainty of involvement with the role
    private Integer DateEnd; //end of involvement with the role
    private Integer DateStart; //start of involvement with the role
    private String Comments; //general notes / comments
    private Integer Precedence; //sequence of involvement with the role
    private String PersonRef;
    private Event Event; //not tested
    private Organisation Organisation; //not tested
        
    /* Constructors */
    //basic constructor
    HasRole(String role) {
        this.Name = role;
    }
    //complete constructor - not called/tested
    HasRole(String role, boolean certain, Integer DateStart, Integer DateEnd, String Comments,
            Integer Order, Integer Precedence, String Ref, String OrgName) {
        this.Name = role;
        setCertain(certain);
        setStart(DateStart);
        setEnd(DateEnd);
        setComments(Comments);
        setPrecedence(Precedence);
        setPersonRef(Ref);
        this.Organisation = new Organisation(OrgName);
        this.Event = new Event();
    }
    /* Setters */
    private void setCertain(boolean certain){
        this.Certain = certain;
    }
    private void setComments(String Comments){
        this.Comments = Comments;
    }
    private void setStart(Integer start){
        if(start >= 1066 && start <= 20151231)
            this.DateStart = start;
        else
            this.DateStart = -1;
    }
    
    private void setEnd(Integer end){
        if(end >= 1066 && end <= 20151231)
            this.DateEnd = end;
        else
            this.DateEnd = -1;
    }
    
    private void setPrecedence(Integer Precedence){
        if(Precedence >= 1)
            this.Precedence = Precedence;
        else
            this.Precedence = -1;
    }
    private void setPersonRef(String ref){
        this.PersonRef = ref;
    }
    
}
/**
 * An array of HasTitle is used to store all titles of a Person in a record. The
 * constructor enforces the required field HasTitle.Title.Name to be filled.
 */
class HasTitle{
	private boolean Certain; //certainty of having title
    private Integer DateEnd; //end of having title
    private Integer DateStart; //start of having title
    private String Comments; //general notes / comments
    private Integer Precedence; //sequence of title
    private Title Title;
    
    /* Constructors */
    //basic constructor
    HasTitle(String Title){
    	this.Title = new Title(Title);
    }
    //basic constructor with Alias - not called/tested
    HasTitle(String Title, String[] HasAlias){
    	this.Title = new Title(Title, HasAlias);
    }
   //complete constructor - not called/tested
    HasTitle(String Title,boolean certain, Integer DateStart, Integer DateEnd, String Comments,
            Integer Precedence){
    	this.Title = new Title(Title);
    	setCertain(certain);
        setStart(DateStart);
        setEnd(DateEnd);
        setComments(Comments);
        setPrecedence(Precedence);
        
    }
    /* Setters */
    private void setCertain(boolean certain){
        this.Certain = certain;
    }
    private void setComments(String Comments){
        this.Comments = Comments;
    }
    private void setStart(Integer start){
        if(start >= 1066 && start <= 20151231)
            this.DateStart = start;
        else
            this.DateStart = -1;
    }
    
    private void setEnd(Integer end){
        if(end >= 1066 && end <= 20151231)
            this.DateEnd = end;
        else
            this.DateEnd = -1;
    }
    
    private void setPrecedence(Integer Precedence){
        if(Precedence >= 1 && Precedence <= 25)
            this.Precedence = Precedence;
        else
            this.Precedence = -1;
    }
    
    /* Title class */
    static class Title{
    	private String Comments; //general notes / comments
    	private String Name; // REQUIRED - name of title
    	private HasAlias[] HasAlias; //title alias
    	
    	/* Constructors */
    	//basic constructor
    	Title(String Name){
    		this.Name = Name;
    	}
    	//basic constructor with Alias - not called/tested
    	Title(String Name, String[] HasAlias){
    		this.Name = Name;
    		this.HasAlias = new HasAlias[HasAlias.length];
            for (int i = 0; i < HasAlias.length; i++) {
                this.HasAlias[i] = new HasAlias(HasAlias[i]);
            }
    	}
    	//complete constructor - not called/tested
    	Title(String Name, String Comments, String genre, String type, String[] HasAlias){
    		this.Name = Name;
    		setComments(Comments);
    		this.HasAlias = new HasAlias[HasAlias.length];
            for (int i = 0; i < HasAlias.length; i++) {
                this.HasAlias[i] = new HasAlias(HasAlias[i]);
            }
    		
    	}
    	/* Setters */
    	private void setComments(String Comments){
            this.Comments = Comments;
        }
    	
    	/*Alias for Title class*/
    	static class HasAlias{
    		private String Comments; //general notes / comments
    		private Title Title;
            //property Type not included
    		/* Constructors */
    		//basic constructor with required field
    		HasAlias(String Name){
    			this.Title = new Title(Name);
    		}
    		//complete constructor - not called/tested
    		HasAlias(String Comments, String Name){
    			setComments(Comments);
    			this.Title = new Title(Name);
    		}
    		/* Setters */
    		private void setComments(String Comments){
    	        this.Comments = Comments;
    	    }
    		
    	}
    	
        
    }
    
}
/**
 * An array of HasOrigin is used to store the origin/provenance associated with a Person in
 * a record. The constructor enforces the required field HasOrigin.Place.Name to
 * be filled.
 */
class HasOrigin{
	private boolean Certain; //certainty of identification of place of origin or birth
    private Integer DateEnd; //end of dates at place of origin or birth
    private Integer DateStart; //start of date(s) at place of origin or birth
    private String Comments; //general notes / comments
    private Place Place;
    
    HasOrigin(String Name){
        this.Place = new Place(Name);
    }
    //complete constructor - not called/tested
    HasOrigin(String Name,boolean certain, Integer DateStart, Integer DateEnd, String Comments){
        this.Place = new Place(Name);
    	setCertain(certain);
        setStart(DateStart);
        setEnd(DateEnd);
        setComments(Comments);
        
    }
    private void setCertain(boolean certain){
        this.Certain = certain;
    }
    private void setComments(String Comments){
        this.Comments = Comments;
    }
    private void setStart(Integer start){
        if(start >= 1066 && start <= 20151231)
            this.DateStart = start;
        else
            this.DateStart = -1;
    }
    
    private void setEnd(Integer end){
        if(end >= 1066 && end <= 20151231)
            this.DateEnd = end;
        else
            this.DateEnd = -1;
    }
    
	
}





/**
 * An object of Place is created by the HasPlace class to store the name of a place associated with a Person in
 * a record. The constructor enforces the required field Place.Name to be filled.
 */
class Place{
	
	private String Name; //REQUIRED - name of place
	private HasAlias[] HasAlias; //place has alias(es)
	private IsWithin[] IsWithin; //place is wholly or partially contained by another place
	private String Comments; //general notes / comments
	private HasPlace[] HasPlace;
    //property Type not implemented
	/* Constructors */
	//basic constructor
	Place(String Name){
		this.Name = Name;
	}
	
	//complete constructor - not called/tested
	Place(String Name, String[] HasAlias, String[] IsWithinPlaceName,String Comments, String[] HasPlace){
		this.Name = Name;
		this.HasAlias = new HasAlias[HasAlias.length];
        for (int i = 0; i < HasAlias.length; i++) {
            this.HasAlias[i] = new HasAlias(HasAlias[i]);
        }
        this.IsWithin = new IsWithin[IsWithinPlaceName.length];
        for (int i = 0; i < IsWithinPlaceName.length; i++) {
            this.IsWithin[i] = new IsWithin(IsWithinPlaceName[i]);
        }
        this.HasPlace = new HasPlace[HasPlace.length];
        for (int i = 0; i < HasPlace.length; i++) {
            this.HasPlace[i] = new HasPlace(HasPlace[i]);
        }
		setComments(Comments);
	}
	/* Setters */
	private void setComments(String Comments){
        this.Comments = Comments;
    }
	
	/* Alias class for Place */
	static class HasAlias{ 
		private String Comments;
		private Place Place;
        //property Type not included
		/*  Constructors */
		//basic constructor
        //must call this. Place class requires Name field
		HasAlias(String Name){
			this.Place = new Place(Name);
		}
		//complete constructor not called/tested
		HasAlias(String Name, String Comments){
			this.Place = new Place(Name);
			setComments(Comments);
		}
		/* Setters */
		
		private void setComments(String Comments){
	        this.Comments = Comments;
	    }
		
	}
	/* IsWithin class for Place*/
	class IsWithin{
		private String Comments;
		private Place Place;
		
		//must call this. Place class requires Name field
		IsWithin(String PlaceName){
			this.Place = new Place(PlaceName);
		}
        //complete constructor not called/tested
		IsWithin( String PlaceName, String Comments){
			this.Place = new Place(PlaceName);
			setComments(Comments);
			
		}
		private void setComments(String Comments){
	        this.Comments = Comments;
	    }
		
	}
    
	class HasPlace{
		private String Comments;
		private Place Place;
		
		//must call this. Place class requires Name field
		HasPlace(String PlaceName){
			this.Place = new Place(PlaceName);
		}
        //complete constructor not called/tested
		HasPlace( String PlaceName, String Comments){
			this.Place = new Place(PlaceName);
			setComments(Comments);
			
		}
		private void setComments(String Comments){
	        this.Comments = Comments;
	    }
		
	}
}
/**
 * Class Relation defines a generalised/abstract idea of all relation properties included in the schema.
 * The constructor enforces the required field Relation.Ref to be filled.
 * It might include fields that are present in all of the sub-relation properties.
 * Each sub-relation ensures by its constructor that the right fields are being filled.
 */

class Relation<T> {
    protected String Comments; //general notes / comments
    protected float Score; //weighting for the relation assertion
    protected PersonNoRecursion Person; //2nd person reference 
    
    //basic constructor
    Relation(String Ref){
    	this.Person = new PersonNoRecursion(Ref);
    }
    //basic constructor with relation weight 
    Relation(String Ref, float Score){
    	this.Person = new PersonNoRecursion(Ref);
        this.Score = Score;
    }
    //complete constructor not called/tested
    Relation(String Ref, float Score, String Comments){
    	this.Person = new PersonNoRecursion(Ref);
        this.Score = Score;
        
    }
    /* Setters */
    private void setComments(String Comments){
        this.Comments = Comments;
    }
    class PersonNoRecursion {

       final private String Ref;  // REQUIRED
       public PersonNoRecursion(String Ref) {
           this.Ref = Ref;
       }
   }
}
/**
 * An array of HasParent is used to store all parent-child relation between a Person 
 * and the rest of the occurrences.
 * The constructor enforces the required field HasParent.Ref and HasParent.Score to be filled.
 */
class HasParent extends Relation<Person> {
   
    private boolean Certain; //certainty of having the relation
    private Integer DateStart; //start of having the relation
    private Integer DateEnd; //end of having the relation
    private Integer Precedence; //sequence of relation
    //property Type not included
    
	/* Constructors */
	//basic constructor 
	HasParent(String Ref) {
		super(Ref);
	}
	//basic constructor with weighting the relation/tested
    HasParent(String Ref, float Score) {
		super(Ref, Score);
		
    }
    //complete constructor not called/tested
    HasParent(String Ref, float Score, boolean Certain, Integer DateStart, Integer DateEnd, Integer Precedence) {
		super(Ref, Score);
        setCertain(Certain);
        setStart(DateStart);
        setEnd(DateEnd);
        setPrecedence(Precedence);
		
    }
    /* Setters */
    private void setCertain(boolean certain){
        this.Certain = certain;
    }
    private void setStart(Integer start){
        if(start >= 1066 && start <= 20151231)
            this.DateStart = start;
        else
            this.DateStart = -1;
    }
    
    private void setEnd(Integer end){
        if(end >= 1066 && end <= 20151231)
            this.DateEnd = end;
        else
            this.DateEnd = -1;
    }
    
    private void setPrecedence(Integer Precedence){
        if(Precedence >= 1 && Precedence <= 10)
            this.Precedence = Precedence;
        else
            this.Precedence = -1;
    }
    
    

}
/**
 * An array of SameAs is used to store all confidence score between a Person 
 * and the rest of the occurrences.
 * The constructor enforces the required field SameAs.Ref and SameAs.Score and SameAs.TimeStamp to be filled.
 */
class SameAs extends Relation<Person> {

	private String TimeStamp; //date/time the 'same as' assertion was mades
    private String Id; //id of the relevant batch or result set stored in an auditing database, eg. MongoDB
    private String Type; //algorithmic methods employed in the 'same as' assertion
    //property Genre not included
    
	//basic constructor with required fields
    SameAs(String Ref, float Score) {
        super(Ref,Score);
        this.TimeStamp = getTimeStamp();
    }
    //complete constructor - not called/tested
    SameAs(String Ref, float Score, String TimeStamp, String Id, String Type) {
    	super(Ref,Score);
        this.TimeStamp = TimeStamp;
        this.Id = Id;
        this.Type = Type;
    }

    
}

/**
 * HasEndDate class is used to store the end date of an Event/Person reference
 * Implemented special constructor for Leiden's data. 
 */
class HasEndDate{
	private String Comments; //general notes / comments 
	/* Detailed fields of EndDate*/
	private Year Year; 
	private Month Month;
	private Day Day;
	private Time Time;
	
	/* Constructors */
	//basic constructor - not called/tested
	HasEndDate(Integer YearValue, Integer MonthValue, Integer dayValue, String Timestamp){
		this.Year = new Year(YearValue);
		this.Month = new Month(MonthValue);
		this.Day = new Day(dayValue);
		this.Time = new Time(Timestamp);
	}
	
	//basic constructor
	//this constructor is called for Leiden's data. The data includes dates in the format : "1547-01-29" or "1547"
	public HasEndDate(String fulldate) {
		
		if (fulldate.contains("-")){
			String[] parts = fulldate.split("-");
			if(parts.length == 3){
				this.Year = new Year(Integer.parseInt(parts[0]));
				this.Month = new Month(Integer.parseInt(parts[1]));
				this.Day = new Day(Integer.parseInt(parts[2]));
				
			}else if(parts.length == 2){
				this.Year = new Year(Integer.parseInt(parts[0]));
				this.Month = new Month(Integer.parseInt(parts[1]));
			}
			
		}
		else{
			this.Year = new Year(Integer.parseInt(fulldate));
		}
	}
}
/**
 * HasStarDate class is used to store the start date of an Event/Person reference
 * Implemented special constructor for Leiden's data. 
 */
class HasStartDate{
	private String Comments;
	private Year Year;
	private Month Month;
	private Day Day;
	private Time Time;
	
	//basic constructor - not called/tested
	HasStartDate(Integer YearValue, Integer MonthValue, Integer DayValue, String Timestamp){
		this.Year = new Year(YearValue);
		this.Month = new Month(MonthValue);
		this.Day = new Day(DayValue);
		this.Time = new Time(Timestamp);
	}
	
	//basic constructor
	//this constructor is called for Leiden's data. The data includes dates in the format : "1547-01-29" or "1547"
	public HasStartDate(String fulldate) {
			
		if (fulldate.contains("-")){
			String[] parts = fulldate.split("-");
			if(parts.length == 3){
				this.Year = new Year(Integer.parseInt(parts[0]));
				this.Month = new Month(Integer.parseInt(parts[1]));
				this.Day = new Day(Integer.parseInt(parts[2]));
			
			}else if(parts.length == 2){
				this.Year = new Year(Integer.parseInt(parts[0]));
				this.Month = new Month(Integer.parseInt(parts[1]));
			}
			
		}
		else{
			this.Year = new Year(Integer.parseInt(fulldate));
		}
	}

}
/**
 * Year class 
 */
class Year{
	private String Comments; //general notes / comments
	private String Name; // description of Year
	private Integer Value; //REQUIRED - Year
	
	//basic constructor
	Year(Integer Value){
		setValue(Value);
	}
	//setter
	private void setValue(Integer Value){
        if(Value >= 1066 && Value <= 2015)
            this.Value = Value;
        else
            this.Value = -1;
    }
}
/**
 * Month class 
 */
class Month{
	private String Comments; //general notes / comments
	private String Name; // description of Year
	private Integer Value; //REQUIRED - Month
	//basic constructor
	Month(Integer Value){
		setValue(Value);
	}
	//setter
	private void setValue(Integer Value){
        if(Value >= 1 && Value <= 12)
            this.Value = Value;
        else
            this.Value = -1;
    }
}
/**
 * Day class 
 */
class Day{
	private String Comments; //general notes / comments
	private String Name; // description of Year
	private Integer Value; //REQUIRED - Day
	//basic constructor
	Day(Integer Value){
		setValue(Value);
	}
	//setter
	private void setValue(Integer Value){
        if(Value >= 1 && Value <= 31)
            this.Value = Value;
        else
            this.Value = -1;
    }
}
/**
 * Time class 
 */
class Time{
	private String Comments; //general notes / comments
	private String Name; // description of Year
	private String Timestamp; //REQUIRED - time
	
	Time(String Timestamp){
		this.Timestamp = Timestamp;
	}
	
}

/**
 * Source class - Implemented only basic constructor. Included only basic fields
 */
class Source{
	//no required fields
	private String Comments;
	private String Name;
	
	//basic constructor
	Source(){}

	//full constructor - not called/tested
	Source(String Comments, String Name){
		setComments(Comments);
		setName(Name);

	}
	
	/*Setters*/
	private void setComments(String Comments){
        this.Comments = Comments;
    }
	private void setName(String Name){
        this.Name = Name;
    }
	
}
/**
 * Document class - Implemented only basic constructor. Included only basic fields
 */
class Document {

    private String Ref;
    private InContainer InContainer;
    private HasStartDate HasStartDate;
	private HasEndDate HasEndDate;
	//property Type not included
	//basic constructor
    public void setRef(String Ref) {
        this.Ref = Ref;
    }
    /* Setters and getters */
    public InContainer getContainer() {
        if (this.InContainer == null) {
            this.InContainer = new InContainer();
        }
        return this.InContainer;
    }
    
    public void setStartDate(String Timestamp) {
        if (this.HasStartDate == null) {
            this.HasStartDate = new HasStartDate(Timestamp);
        }
        
    }
    public void setEndDate(String Timestamp) {
        if (this.HasEndDate == null) {
            this.HasEndDate = new HasEndDate(Timestamp);
        }
        
    }
    
}
/**
 * InContainer class - Implemented only basic constructor. Included only basic fields
 */
class InContainer {

    private Document Document; //parent data structure in which person is found (eg. webpage or spreadsheet) 
    private Source Source;  //the wider document container
    private String Comments; //general notes / comments
    
    /* Getters */
    public Document getDocument() {
        if (this.Document == null) {
            this.Document = new Document();
        }
        return this.Document;
    }
    
    

	public Source getSource() {
        if (this.Source == null) {
            this.Source = new Source();
        }
        return this.Source;
    }
}
/**
 * InContainer class in Sentence level - Implemented only basic constructor. Included only basic fields
 */
class SentenceContainer {                               

    final private Context Context;
    //basic constructor 
    public SentenceContainer(String OrigOccurrence) {
    	this.Context = new Context(OrigOccurrence);
	}
    //full constructor - not called/tested
    SentenceContainer(String context, String OrigOccurrence) {
        this.Context = new Context(context, OrigOccurrence);
    }
    
    

	/**
     * Context class in Sentence level - Implemented only basic constructor. Included only basic fields
     */
    class Context {

        private String Name;  // REQUIRED: origOccurrence
        private String Ref;         // sentenceId
        private InContainer InContainer;
        //constructor
        Context(String Name) {
            this.Name = Name;
        }
        //full constructor - not called/tested
        Context(String Ref,String Name) {
        	this.Ref = Ref;
            this.Name = Name;
        }
        /* Getters */
        public InContainer getContainer() {
            if (this.InContainer == null) {
                this.InContainer = new InContainer();
            }
            return InContainer;
        }

    }
    /*Setters*/
    
    public void setOrigOccurrence(String OrigOccurrence) { //REQUIRED
        this.Context.Name = OrigOccurrence;
    }
    
    public void setSentenceId(String sentenceId) {
        this.Context.Ref = sentenceId;
    }
    
    public void  setSectionId(String sectionId) {
        this.Context.getContainer().getDocument().setRef(sectionId);
    }

    public void setHasEndDate(String appearanceDate) {
        this.Context.getContainer().getDocument().setEndDate(appearanceDate);
    }
    public void setHasStartDate(String appearanceDate) {
        this.Context.getContainer().getDocument().setStartDate(appearanceDate);
    }
    public void setFileId(String fileId) {
        this.Context.getContainer().getDocument().getContainer().getDocument().setRef(fileId);

    }
    
}
/////////////////////////NOT TESTED//////////////////////////////
class HasDate{
	private String Comments; 
	private Year Year;
	private Month Month;
	private Day Day;
	private Time time;
	
	
	HasDate(Integer YearValue, Integer MonthValue, Integer DayValue, String Timestamp){
		this.Year = new Year(YearValue);
		this.Month = new Month(MonthValue);
		this.Day = new Day(DayValue);
		this.time = new Time(Timestamp);
	}
	
	
	
}

class HasEvent{
	private String Comments;
	private boolean Certain;
	private Event Event;
	
	HasEvent(){}
	
	private void setComments(String Comments){
        this.Comments = Comments;
    }
	private void setCertain(boolean Certain){
        this.Certain = Certain;
    }
}
class Event{
	private String Comments;
	private HasDate HasDate;
	private HasEndDate HasEndDate;
	private HasStartDate HasStartDate;
	private HasOrganisation HasOrganisation;
	private HasPlace HasPlace;
	
	Event(){}

	private void setComments(String Comments){
        this.Comments = Comments;
    }
	
}


/**
* An array of HasAge is used to store the age of a Person in a
* record. 
*/
class HasAge {

    final private Age Age;
    private boolean Certain; 
    private Integer DateEnd; 
    private Integer DateStart; 
    private String Comments;
    
    HasAge(Integer start) {
        this.Age = new Age(start);
    }
    
    
    class Age {

        private String Name; 
        private Integer End;
        private Integer Start;
        private String Comments;
        //basic constructor 
        Age(Integer start) {
            setStart(start);
        }

        private void setStart(Integer start){
            if(start >= 0 && start <= 120)
                this.Start = start;
            else
                this.Start = -1;
        }
        
        
    }
    
}
class HasOrganisation{
	private Integer DateEnd;
    private Integer DateStart; 
    private String Comments;
    private Integer Precedence;
    private Organisation Org;
    
    HasOrganisation(String OrgName){
    	this.Org = new Organisation(OrgName);
    	
    }
    
    
    
}
/**
* An array of HasAge is used to store the age of a Person in a
* record. 
*/
class HasAlias {

    final private Alias Alias;
    private boolean Certain;
    private Integer DateEnd; 
    private Integer DateStart; 
    private String Comments;
    
    
    
    //basic constructor with required fields
    HasAlias(String AliasName, String ParticleName) {
        this.Alias = new Alias(AliasName, ParticleName);
    }

    static class Alias {

        private String AliasName;
        private String Comments;
        private HasParticle HasParticle;
        
       //basic constructor with required fields
        Alias(String AliasName, String ParticleName) {
            setName(AliasName);
            this.HasParticle = new HasParticle(ParticleName);
        }
        
        private void setName(String AliasName){
            this.AliasName = AliasName;
        }
       
        
   }
    
}
    


class HasAward{
	private boolean Certain; 
	private String Campaign;
    private Integer DateEnd; 
    private Integer DateStart; 
    private String Comments;
    private String Place;
    private Award Award;
    
    //basic constructor
    HasAward(String Name){
    	this.Award = new Award(Name);
    }
   
    
    static class Award{
    	private String Comments;
    	private String Name;
    	private HasAlias HasAlias;
    	//basic constructor only implemented
    	Award(String Name){
    		this.Name = Name;
    	}
    	
    	static class HasAlias{
    		private String Comments;
    		
    		//basic constructor - no required fiedls
    		HasAlias(){}

    	}
    }
    
}



class Organisation{
	private String Name;
	private String Comments;
	private String Ref;
	private HasAlias HasAlias;
	//implemented only basic constructor
	Organisation(String Name){
		this.Name = Name;
	}
	
	static class HasAlias{
		private String Comments;

		//basic constructor - not required fields
		HasAlias(){
		}
	
	}
}

class HasNationality{
	private boolean Certain; 
    private Integer DateEnd; 
    private Integer DateStart; 
    private String Comments;
    private Integer Precedence;
    private Nationality Nationality;
    
    //basic constructor
    HasNationality(String Name){
    	this.Nationality = new Nationality(Name);
    }
    
    
    
    class Nationality{
    	private String Comments;
    	private String Name;
    	private String Ref;
    	//basic constructor
    	Nationality(String Name){
    		this.Name = Name;
    	}
    	
    	
    }
}



/**
 * An array of HasChild is used to store all parent-child relation between a Person 
 * and the rest of the occurrences.
 * The constructor enforces the required field HasChild.Ref and HasChild.Score to be filled.
 */
class HasChild extends Relation<Person> {
    
	
    HasChild(String Ref, float Score) {
		super(Ref, Score);
		
	}
   
}
class HasSpouse extends Relation<Person> {

    HasSpouse(String Ref, float Score) {
		super(Ref, Score);	
	}

}
class HasRelation extends Relation<Person> {
	//basic constructor
    HasRelation(String Ref, float Score) {
		super(Ref, Score);
	}
   
}
class HasSibling extends Relation<Person> {
    
    HasSibling(String Ref, float Score) {
		super(Ref, Score);
	}

}

class HasReference{
	private boolean Certain; 
    private Integer DateEnd; 
    private Integer DateStart; 
    private String Comments;
    private Reference Reference;
    
    HasReference(String Name){
    	this.Reference = new Reference(Name);
    }

    static class Reference{
    	private String Comments;
    	private String Name;

    	
    	//basic constructor
    	Reference(String Name){
    		this.Name = Name;
    	}
    	
    }
}

/**
 * An array of HasPlace is used to store all places associated with a Person in
 * a record. The constructor enforces the required field HasRole.Place.Name to
 * be filled.
 */
class HasPlace {

    private Place Place;
    private Integer DateEnd; //end of involvement of event with place
    private Integer DateStart; //start of involvement of event with place
    private String Comments; //general notes / comments
    private Integer Precedence; //sequence of involvement of event with place
    
    /* Constructors */
    //basic constructor 
    HasPlace(String place) {
        this.Place = new Place(place);
    }
    //complete constructor - not called
    HasPlace(String place, String Comments, Integer start, Integer end, Integer Precedence) {
        this.Place = new Place(place);
        setComments(Comments);
        setEnd(end);
        setStart(start);
        setPrecedence(Precedence);
    }
    
    /* Setters */
    private void setComments(String Comments){
        this.Comments = Comments;
    }
    private void setStart(Integer start){
        if(start >= 1066 && start <= 20151231)
            this.DateStart = start;
        else
            this.DateStart = -1;
    }
    
    private void setEnd(Integer end){
        if(end >= 1066 && end <= 20151231)
            this.DateEnd = end;
        else
            this.DateEnd = -1;
    }
    
    private void setPrecedence(Integer Precedence){
        if(Precedence >= 1 && Precedence <= 25)
            this.Precedence = Precedence;
        else
            this.Precedence = -1;
    }
}
