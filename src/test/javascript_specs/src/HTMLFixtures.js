var HTMLFixtures = function() {
	this.loadPageHTML = function() {
		setFixtures('<div class="dummy" action="">' + '<input class="popupInputDate">' + ' <input class="popupInputDate">' + '</div>');
	};
	this.htmlloadingHTML = function() {
		setFixtures('<div class="dummy">' + '<div class="domainHead">' + '<h1></h1>' + '<div class="breadCrumb">' + '<a href="#"></a>' + '<a href="#"></a>' + '</div>' + '<div class="loggerName"></div>' + '<div id="snwadminSearch">' + '	<h1></h1>' + '</div>' + '</div>');
	};

	this.screenloadHTML = function() {
		setFixtures('<div class="addNew"></div>' + '<div class="upArrow"></div>');
	};

	this.bindeventHTML = function() {
		setFixtures('<div class="dummy">' + '<div class="circlePlus"></div>' + '<div class="loginPrefix"></div>' + '<div id="snw_License"></div>' + '<div id="snw_License1"></div>' + '<div id="licence-number"></div>' + '<div id="snwlicense-number"></div>' + '<div class="domainName"></div>' + '<div class="closeAN"></div>' + '<div class="popupBox">' + '<div class="subscription-end"></div>' + '</div>' + '<div class="popupBox">' + '<div class="loginPrefix"></div>' + '</div>' + '<div class="addschlpopupBox">' + '<div class="loginprefix"></div>' + '</div>' + '<div class="submitdistrict"></div>' + '<div class="cancelistrict"></div>' + '<div class="editsubmit"></div>' + '<div class="editcancel"></div>' + '<div class="schleditsubmit"></div>' + '<div class="editwelcome"></div>' + '<div class="editAddSchool"></div>' + '<div class="schlsubmit"></div>' + '<div class="schlcancel"></div>' + '<div class="loginPerson"></div>' + '<div class="createpopupradio">' + '<input type="text">' + '</div>' + '<div class="disTrictList"></div>' + '<div class="showEntry"></div>' + '<div class="NextCir"></div>' + '<div class="PreviousCir"></div>' + '<div class="DomainNext"></div>' + '<div class="DomainPrevious"></div>' + '<div class="domain-showEntry"></div>' + '<div class="newDomTab">' + '<ul><li></li></ul>' + '</div>' + '<div class="adminNav">' + '<ul><li></li></ul>' + '</div>' + '<div class="adminNav">' + '<ul><li></li><li></li><li></li></ul>' + '</div>' + '<div class="entireCatalog"></div>' + '<div class="SlctvCrses"></div>' + '<div class="corsecatalog"></div>' + '<div class="coursSelect"></div>' + '<div class="searchbox"></div>' + '<div class="SearchClick"></div>' + '<div class="distrctadd"></div>' + '<div class="catalog-list">' + '<input type="text">' + '</div>' + '<div class="edit"></div>' + '<div class="addschool"></div>' + '<div class="addschlpopupBox">' + '<div class="addschoollist"></div>' + '</div>' + '<div class="editschlpopupBox">' + '<div class="addschoollist"></div>' + '</div>' + '<div class="addschlpopupBox">' + '<div class="schooldistrictlist"></div>' + '</div>' + '<div class="editschlpopupBox">' + '<div class="schooldistrictlist"></div>' + '</div>' + '<div class="corsecatalogList">' + '<input type="checkbox">' + '</div>' + '<div id="snwselectCourse">' + '<input type="text">' + '</div>' + '<div id="snwSelectCourse">' + '<input type="text">' + '</div>' + '<div class="editSchoolpop"></div>' + '<div id="snwreportsLienceLi1"></div>' + '<div id="snwreportsLienceLi2"></div>' + '<div class="reportfisrtedit"></div>' + '<div class="reportpost2"></div>' + '<div class="adminNav">' + '<ul><li></li></ul>' + '</div>' + '<div class="stateListing"></div>' + '<div class="domainListing"></div>' + '</div>');
	};

	this.saveSchoolHTML = function() {
		setFixtures('<div class="dummy">' + '<div class="formElementSelector">' + '<input type="text" value="disrict" class="schooldistrictlist">' + '<input type="text" value="name" class="schldomainname">' + '<input type="text" value="loginPrefix" class="loginprefix">' + '<input type="text" value="externalId" class="externalid">' + '<input type="text" value="licenseType" class="licensetype">' + '<input type="text" value="licensePool" class="license-pool">' + '<input type="text" value="12" class="license-number">' + '<input type="checkbox" class="pilot-domain">' + '<input type="text" value="10-30-2015" class="date-field">' + '</div>' + '</div>');
	};

	this.loginouteventHTML = function() {
		setFixtures('<div class="dummy">' + '<div class="LogOuticon"></div>' + '</div>');
	};

	this.statelistchangeHTML = function() {
		setFixtures('<div class="dummy">' + '<input type="text" class="statename" value="statename">' + '<input type="text" class="domain-showEntry">' + '</div>');
	};
	this.cursorwaitHTML = function() {
		setFixtures('<div class="dummy">' + '<div class="grayBg"></div>' + '</div>');
	};
	this.cursordefaultHTML = function() {
		setFixtures('<div class="dummy">' + '<div class="grayBg"></div>' + '</div>');
	};
	this.updateCourseListHTML = function() {
		setFixtures('<div class="dummy">' + '<div class="catalog-list">' + '<input type="checkbox" checked="checked" class="checkbox">' + '<input type="checkbox" checked="checked" class="checkbox">' + '<input type="checkbox" checked="checked" class="checkbox">' + '<input type="checkbox" checked="checked" class="checkbox">' + '<input type="checkbox" checked="checked" class="checkbox">' + '</div>' + '<div class="corseselectList"><ul></ul></div>' + '</div>');
	};
	this.domainFormSubmitHTML = function() {
		setFixtures('<div class="dummy">' + '<div class="formElementSelector" data-domain-id="125478">' + '<div class="catalog-list">' + '<input type="checkbox" checked="true" domain-id="1">' + '<input type="checkbox" checked="true" domain-id="3">' + '<input type="checkbox" checked="true" domain-id="2">' + '<input type="checkbox" checked="true" domain-id="5">' + '</div>' + '<div class="course-list">' + '<input type="checkbox" checked="true" course-id="1">' + '<input type="checkbox" checked="true" course-id="3">' + '<input type="checkbox" checked="true" course-id="2">' + '<input type="checkbox" checked="true" course-id="5">' + '</div>' + '<input type="text" value="state" class="state-code">' + '<input type="text" value="name" class="domain-name">' + '<input type="text" value="loginPrefix" class="login-prefix">' + '<input type="text" value="externalId" class="external-id">' + '<input type="checkbox" checked="checked" class="entire-catalog">' + '<input type="text" value="10-7-2015" class="popupInputDate">' + '<input type="text" value="6-12-2016" class="popupInputDate">' + '<input type="text" value="licenseType" class="license-type">' + '<input type="text" value="5" class="license-number">' + '<input type="text" value="licensePool" class="license-pool">' + '<input type="checkbox" class="pilot-domain">' + '<input type="text" value="12-31-2015" class="pilot-end">' + '</div>' + '</div>');
	};
	this.editnewfunctionHTML = function() {
		setFixtures('<div class="dummy">' + '<div class="editpopupBox">' + '<div class="course-list">' + '<ul><li>data</li><li>data</li></ul>' + '</div>' + '</div>' + '</div>');
	};
};