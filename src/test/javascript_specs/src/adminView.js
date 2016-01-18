$('document').ready(function() {
    var adminModel = new AdminModel();
    var adminView = new AdminView(adminModel);
    adminView.init();
    adminView.cursorwait();
});
var AdminView = function(model) {
    var self = this;
    this.model = model;
    this.init = function() {
        this.model.set('view', self);
        this.model.ajaxCalConfig();
    };

    this.loadPage = function() {
        if (!sessionStorage.getItem('data')) {
            self.logout();
        }
        var parseJson = JSON.parse(sessionStorage.getItem('data'));
        self.model.set('_globalSccessToken', parseJson.loginToken);
        self.getConfig();
        self.loadingdistrict();
        self.screenload();
        self.htmlloading();
        self.bindevent();
        $(".popupInputDate").each(function() {
            $(this).datepicker({
                dateFormat: "mm-dd-yy"
            });
        });
    };

    this.htmlloading = function() {
        var parseJson = JSON.parse(sessionStorage.getItem('data'));
        $('.domainHead h1').html(parseJson.domainname);
        $('.breadCrumb a:eq(0)').html(parseJson.userspace);
        $('.breadCrumb a:eq(1)').html(parseJson.username);
        $('.loggerName').html(parseJson.firstname + " " + parseJson.lastname);
        $('#snwadminSearch h1').html('Welcome' + ' ' + parseJson.firstname + " " + parseJson.lastname);
        self.model.set('_globalSccessToken', parseJson.loginToken);
    };

    this.screenload = function() {
        $('.addNew,.upArrow').hide();
        $('.newDomTab li').each(function(k) {
            $('.newDomTab li').attr('id', function(k) {
                return "tab_" + k;
            });
        });
        $('.listBox').each(function() {
            $('.listBox').attr('id', function(l) {
                return "panel_" + l;
            });
        });
    };

    this.bindevent = function() {
        this.model.set({
            'win_height': $(window).height(),
            'win_width': $(window).width()
        });
        $('.circlePlus').off('click').on('click', function() {
            self.circlePlusHandler();
        });
        var result = self.model.get('configResult'),
            inputDomVal = '',
            splitArry = [],
            dataWraper = '',
            loginPrefx = '',
            loginUndifndChk = '',
            loginArray = [];
        $('.loginPrefix,#snw_Enter_Login_Prefix').keyup(function() {
            self.loginKeyUpPrefix($(this));
        });
        $('#snw_License').off('keyup').on('keyup', function() {
            self.numberValidation($(this));
        });
        $('#snw_License1').off('keyup').on('keyup', function() {
            self.numberValidation($(this));
        });
        $('#licence-number').off('keyup').on('keyup', function() {
            self.numberValidation($(this));
        });
        $('#snwlicense-number').off('keyup').on('keyup', function() {
            self.numberValidation($(this));
        });
        $('.domainName,#snw_Enter_School').keyup(function() {
            self.domainNameKeyUp($(this), loginArray);
        });
        $('.closeAN').off('click').on('click', function() {
            $('.grayBg,.popGrayBg,.editschlpopupBox').hide();
            $('.popupBox,.editpopupBox,.addschlpopupBox').hide();
        });
        $('.popupBox .subscription-end,.editpopupBox .subscription-end').unbind('change paste keyup').bind("change paste keyup", function() {
            self.popupBoxHandler();
        });
        $('.popupBox .loginPrefix').off('keydown').on("keydown", function(e) {
            if (e.keyCode === 32) {
                return false;
            }
        });
        $('.addschlpopupBox .loginprefix').off('keydown').on("keydown", function(e) {
            if (e.keyCode === 32) {
                return false;
            }
        });
        $('.submitdistrict').off('click').on('click', function() {
            self.submitDistrictHandler($(this))
        });
        $('.cancelistrict').off('click').on('click', function() {
            $('.grayBg').hide();
            $('.popGrayBg').hide();
            $('.popupBox,.editpopupBox').hide();
        });
        $('.editsubmit').off('click').on('click', function() {
            self.editSubmitHandler($(this))
        });
        $('.editcancel').off('click').on('click', function() {
            $('.grayBg').hide();
            $('.popGrayBg').hide();
            $('.popupBox,.editpopupBox').hide();
        });
        $('.schleditsubmit').off('click').on('click', function() {
            self.schlEditSubmitHandler($(this));
        });
        $('.editwelcome').off('click').on('click', function() {
            self.domainWelcomeLetter();
        });
        $('.editAddSchool').off('click').on('click', function() {
            self.editAddSchool($(this))
        });
        //		34894868
        $('.schlsubmit').off('click').on('click', function() {
            self.schlsubmitHandler($(this));
        });
        $('.schlcancel').off('click').on('click', function() {
            $('.grayBg').hide();
            $('.popGrayBg,.editschlpopupBox').hide();
            $('.addschlpopupBox').hide();
        });
        $('.loginPerson').off('click').on('click', function() {
            $('.LPMenu').toggle();
            return false;
        });
        $('.createpopupradio input,.editpopupradio input').off('change').on('change', function() {
            self.createPopupRadio($(this))
        });
        $('.disTrictList').off('click').on('click', function() {
            self.disTrictListHandler($(this));
        });
        $('.showEntry').off('change').on('change', function() {
            self.showEntryHandler($(this));
        });
        $('.NextCir').off('click').on('click', function() {
            self.NextCirHandler($(this));
        });
        $('.PreviousCir').off('click').on('click', function() {
            self.PreviousCirHandler($(this));
        });
        $('.DomainNext').off('click').on('click', function() {
            self.DomainNextHandler($(this));
        });
        $('.DomainPrevious').off('click').on('click', function() {
            self.DomainPreviousHandler($(this));
        });
        $('.domain-showEntry').off('change').on('change', function() {
            self.domainShowEntryHandler($(this));
        });
        $('.newDomTab li').off('click').on('click', function() {
            self.newDomTabHandler($(this));
        });
        $('.adminNav li').off('click').on('click', function() {
            self.adminNav($(this));
        });
        $('.adminNav li:eq(1)').off('click').on('click', function() {
            self.adminNavFirst($(this));
        });
        $('.entireCatalog').off('click').on('click', function() {
            $('.coursSelect').attr("contenteditable", false).removeClass('disabled');
        });
        $('.SlctvCrses').off('click').on('click', function() {
            $('.coursSelect').attr("contenteditable", true).addClass('disabled');
        });
        $('.corsecatalog').off('click').on('click', function(e) {
            self.courseCatalogHandler(e)
        });
        $('.coursSelect').off('click').on('click', function(e) {
            self.courseSelectHandler(e);
        });
        $('.searchbox').off('keypress').on('keypress', function(e) {
            if (e.keyCode == 13) {
                $('.SearchClick').trigger('click');
            }
        });
        $('.SearchClick').off('click keyup').on('click keyup', function(e) {
            self.SearchClickHandler();
        });
        $(document.body).off("click tap").on("click tap", function(e) {
            self.documentClickHandler(e);
        });
        $('.distrctadd').off('click').on('click', function() {
            self.distrctAddHandler($(this));
        });
        $('.catalog-list input').off('change').on('change', function() {
            self.catalogListInputHandler($(this));
        });
        $('.edit').off('click').on('click', function() {
            self.editHandler($(this));
        });
        $('.addschool').off('click').on('click', function() {
            self.addSchoolHandler($(this));
        });
        $('.addschlpopupBox .addschoollist').off('change').on('change', function() {
            self.addSchlPopupBox($(this));
        });
        $('.editschlpopupBox .addschoollist').off('change').on('change', function() {
            self.addSchlPopupBox($(this));
        });
        $('.addschlpopupBox .schooldistrictlist').off('change').on('change', function() {
            self.editSchoolDistrictList($(this));
        });
        $('.editschlpopupBox .schooldistrictlist').off('change').on('change', function() {
            self.editSchoolDistrictList($(this));
        });
        $('.corsecatalogList input[type="checkbox"]').off().on("click", function() {
            self.courseCatalogListInput($(this));
        });
        $('#snwselectCourse input').off('change').on('change', function() {
            self.radioButton('popupBox');
        });
        $('#snwSelectCourse input').off('change').on('change', function() {
            self.radioButton('editpopupBox');
        });
        $('.editSchoolpop').off('click').on('click', function() {
            self.editSchoolPopHandler($(this));
        });
        $('#snwreportsLienceLi1').off('click').on('click', function() {
            self.snwReportsLienceLiFirst($(this));
        });
        $('#snwreportsLienceLi2').off('click').on('click', function() {
            self.snwreportsLienceLiSec($(this));
        });
        $('.reportfisrtedit').off('click').on('click', function() {
            self.reportfisrteditHandler()
        });
        $('.reportpost2').off('click').on('click', function() {
            self.reportPostSec();
        });
        $('.adminNav li:eq(0)').off('click').on('click', function() {
            self.adminNavZero();
        });
        $('.stateListing').off('click').on('click', function(e) {
            self.stateListingHandler(e);
        });
        $('.domainListing').off('click').on('click', function(e) {
            self.domainListingHandler(e);
        });

        self.loginoutevent();
    };



    this.editSchool = function(fromselector, data_id, stateSelctedVal) {
        var editScholPrntId = '';
        self.model.ajaxObjCall({ //1060
            type: "GET",
            url: self.model.get('_config').getDomainData + '/' + data_id
        }, self.editSchoolData, self.commonError, void 0, {
            successVal: {
                stateSelctedVal: stateSelctedVal,
                fromselector: fromselector
            }
        })
    };

    this.saveSchool = function(formElementSelector, data) {
        var urlChecker = '',
            someDate,
            numberOfDaysToAdd,
            date,
            dateFormat,
            dateSplice,
            Month,
            Datee,
            subscriptionstartdate,
            endPilot_Date;
        createdDomainObj = {};
        // console.log(res.data.customization.edivatelearn.subscriptionstartdate);
        subscriptionstartdate = self.model.get('subscriptionstartdate');
        currentDate = formElementSelector.find(".date-field").val();

        if (formElementSelector.find(".pilot-domain").is(":checked") && new Date(currentDate).getTime() < new Date(subscriptionstartdate).getTime()) {
            self.cursordefault();
            alert('Pilot date should not be less than Subscription Start Date.');
            return;
        }
        if (data === 'create') {

            if (formElementSelector.find(".pilot-domain").is(":checked") && formElementSelector.find(".date-field").val() == "") {
                someDate = new Date();
                numberOfDaysToAdd = 90;
                someDate.setDate(someDate.getDate() + numberOfDaysToAdd);
                date = new Date(someDate);
                dateFormat = date.toLocaleDateString().replace(/\//g, "-");
                dateSplice = dateFormat.split('-');
                Month = dateSplice[0];
                Datee = dateSplice[1];
                if (dateSplice[0].length === 1) {
                    dateSplice[0] = [];
                    dateSplice[0] = '0' + Month;
                }
                if (dateSplice[1].length === 1) {
                    dateSplice[1] = [];
                    dateSplice[1] = '0' + Datee;
                }
                dateSplice.join('-');
                console.log(dateSplice.join('-'))
                endPilot_Date = dateSplice.join('-');
            }
            createdDomainObj = {

                disrict: formElementSelector.find(".schooldistrictlist").val(),
                name: formElementSelector.find(".schldomainname").val(),
                loginPrefix: formElementSelector.find(".loginprefix").val(),
                externalId: formElementSelector.find(".externalid").val(),
                licenseType: formElementSelector.find(".licensetype").val(),
                licensePool: formElementSelector.find('.license-pool').val(),
                numbersOfLicense: formElementSelector.find(".license-number").val(),
                pilotDomain: formElementSelector.find(".pilot-domain").is(":checked"),
                pilotEndDate: endPilot_Date
            };
            urlChecker = self.model.get('_config').createSchool;
        }
        if (data === 'edit') {
            createdDomainObj = {
                domainid: self.model.get('_domainId'),
                disrict: formElementSelector.find(".schooldistrictlist").val(),
                name: formElementSelector.find(".schldomainname").val(),
                loginPrefix: formElementSelector.find(".loginprefix").val(),
                externalId: formElementSelector.find(".externalid").val(),
                licenseType: formElementSelector.find(".licensetype").val(),
                licensePool: formElementSelector.find('.license-pool').val(),
                numbersOfLicense: formElementSelector.find(".license-number").val(),
                pilotDomain: formElementSelector.find(".pilot-domain").is(":checked"),
                pilotEndDate: formElementSelector.find(".date-field").val()
            };
            urlChecker = self.model.get('_config').eidtSchool;
        }
        self.model.ajaxObjCall({ //1142
            type: "POST",
            url: urlChecker,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(createdDomainObj)
        }, self.saveSchoolData, self.commonError, void 0, {
            successVal: {}
        });
    };

    this.radioButton = function(dataClass) {
        console.log(dataClass);
        $('.' + dataClass).find('.selective-course').prop("checked", true);
        $('.' + dataClass).find('.entire-catalog').prop("checked", false);
    };

    this.loginoutevent = function() {
        var result = self.model.get('configResult');
        $('.LogOuticon').off('click').on('click', function() {
            self.loginoutIconClick(result)
        });
    };


    this.loginoutIconClick = function(result) {
        self.model.set('_baseUrl', result.Logouturl);
        self.model.ajaxObjCall({ //1182
            type: "GET",
            url: self.model.get('_baseUrl'),
            crossDomain: true,
            contentType: "application/json",
        }, self.logout, self.commonError, void 0, {
            successVal: {}
        });
    };



    this.sortingDistrict = function() {
        //create global variable and give it start value 1
        var sortBy = "",
            parent = $('.domainContainer > ul'),
            keySelector = ".disTrictList",
            childSelector = $('.domainContainer > ul > li'),
            asc = true;
        var items = parent.children(childSelector).sort(function(a, b) {
            var vA = $.trim($(keySelector, a).text());
            var vB = $.trim($(keySelector, b).text());
            return ((vA < vB) ? -1 : (vA > vB) ? 1 : 0) * (asc ? 1 : -1);
        });
        parent.empty().append(items);

    };

    this.loadingdistrict = function() {
        $('.districtTabBox').remove();
        var html = "",
            result = self.model.get('configResult'),
            state_list = "",
            split_data = {},
            schoolId = '',
            urlStore = result.DistrictList;
        self.model.set('_baseUrl', result.Domainlist);
        self.model.ajaxObjCall({ //1227
            type: "GET",
            url: self.model.get('_baseUrl'),
            crossDomain: true,
            contentType: "application/json",
        }, self.districtOptionLoading, self.commonError, void 0, {
            successVal: {
                html: html,
                split_data: split_data,
                state_list: state_list,
                urlStore: urlStore
            }
        });
    };

    this.statelistchange = function() {
        $('.statename').off('change').on('change', function() {
            self.cursorwait();
            self.model.set('_dropdownCount', 10);
            $('.domain-showEntry').val(self.model.get('_dropdownCount'));
            var html = '',
                result = self.model.get('configResult'),
                idUrl = $(this).val(),
                schlId = '';
            self.model.set({
                '_postId': $(this).val(),
                '_optionselector': $(this).val()
            });
            self.model.set('_baseUrl', result.DistrictList);
            self.model.ajaxObjCall({ //1317
                type: "GET",
                url: self.model.get('_baseUrl') + '/' + idUrl + '?querystring=0&limit=' + self.model.get('_dropdownCount') + '',
                crossDomain: true,
                contentType: "application/json"
            }, self.districtOptionLoadingData, self.commonError, self.nextDomainCallComplete, {
                successVal: {}
            });
        });
    };

    this.cursorwait = function() {
        $('body').css('cursor', 'wait');
        $('.grayBg,.snwLoader').show();
        $('.grayBg,.popGrayBg').css({
            'height': $('body').height()
        });
    };

    this.cursordefault = function() {
        $('body').css('cursor', 'default');
        $('.grayBg,.snwLoader').hide();
    };

    this.getProviderList = function() {
        var html = '',
            result = self.model.get('configResult');
        self.model.ajaxObjCall({ //NKN
            type: "GET",
            url: result.New_District_Popup_Provider,
            crossDomain: true,
            contentType: "application/json"
        }, self.getProviderListDataCall, self.commonError, void 0, {
            successVal: {}
        });
    };

    this.getCourseList = function(courseID) {
        var courseListObj,
            courseListURI = self.model.get('_config').courseList + "/" + courseID;
        $.when($.ajax({
                url: courseListURI,
                beforeSend: function(xhr, settings) {
                    view.beforeSendEventHandler(xhr, settings);
                }
            }))
            .then(function(data, txtStatus, jqXHR) {
                courseListObj = data;
            });
        return courseListObj;
    };

    this.getConfig = function() {
        $.when($.ajax("config.json"))
            .then(function(data, txtStatus, jqXHR) {
                self.model.set('_config', data);
            });
    };

    this.updateCourseList = function() {
        var selectedCourseCatalog = $('.catalog-list:visible').find('input[type="checkbox"]').filter(":checked"),
            totalCourses = [],
            courseListURI,
            courseSelectListHTML = "";
        if (selectedCourseCatalog.length > 0) {
            $(selectedCourseCatalog).each(function(ind) {
                courseListURI = self.model.get('_config').courseList + "/" + $(this).data("domain-id");
                self.model.ajaxObjCall({ //1447
                    url: courseListURI
                }, self.updateCourseData, self.commonError, void 0, {
                    successVal: {
                        selectedCourseCatalog: selectedCourseCatalog,
                        ind: ind
                    }
                });
            });
        } else {
            courseSelectListHTML = '<li>Please select a Course Catalog first.</li>';
            $(".corseselectList ul").html(courseSelectListHTML);
        }
    };

    this.domainWelcomeLetter = function() {
        var name,
            welcomeLetterURI,
            form;
        name = self.model.get('_sessionData').firstname + " " + self.model.get('_sessionData').lastname;
        welcomeLetterURI = self.model.get('_config').welcomeLetter + '/' + $('.editwelcome').data("domain-id") + '/' + name;
        form = $('<form></form>').attr('action', welcomeLetterURI).attr('method', 'get');
        form.appendTo('body').submit().remove();
    };

    this.domainFormValidation = function(formElementSelector, requestType) {
        formElementSelector = $(formElementSelector);
        self.domainFormSubmit(formElementSelector, requestType);
    };

    this.domainFormSubmit = function(formElementSelector, requestType) {
        var catalogListArr,
            Month,
            Datee,
            courseListArr,
            endPilot_Date,
            someDate,
            numberOfDaysToAdd,
            createdDomainObj,
            date,
            dateFormat,
            dateSplice,
            domainRequestURI;
        self.cursorwait();
        formElementSelector = $(formElementSelector);
        catalogListArr = [];
        courseListArr = [];
        formElementSelector.find('.catalog-list input[type="checkbox"]').filter(":checked").each(function() {
            catalogListArr.push($(this).data("domain-id"));
        });
        formElementSelector.find('.course-list input[type="checkbox"]').filter(":checked").each(function() {
            courseListArr.push($(this).data("course-id"));
        });
        endPilot_Date = formElementSelector.find(".pilot-end").eq(0).val();
        if (formElementSelector.find(".pilot-domain").is(":checked") && formElementSelector.find(".pilot-end").eq(0).val() == "") {
            someDate = new Date();
            numberOfDaysToAdd = 90;
            someDate.setDate(someDate.getDate() + numberOfDaysToAdd);
            date = new Date(someDate);
            dateFormat = date.toLocaleDateString().replace(/\//g, "-");
            dateSplice = dateFormat.split('-');
            Month = dateSplice[0];
            Datee = dateSplice[1];
            if (dateSplice[0].length === 1) {
                dateSplice[0] = [];
                dateSplice[0] = '0' + Month;
            }
            if (dateSplice[1].length === 1) {
                dateSplice[1] = [];
                dateSplice[1] = '0' + Datee;
            }
            dateSplice.join('-');
            console.log(dateSplice.join('-'))
            endPilot_Date = dateSplice.join('-');
        }
        self.model.set('_globalCourseCatalog', catalogListArr);
        self.model.set('_globalCourseSelection', courseListArr);
        createdDomainObj = {
            state: formElementSelector.find(".state-code").val(),
            name: formElementSelector.find(".domain-name").val(),
            loginPrefix: formElementSelector.find(".login-prefix").val(),
            externalId: formElementSelector.find(".external-id").val(),
            fullSubscription: formElementSelector.find(".entire-catalog").is(":checked"),
            courseCatalogs: catalogListArr,
            courseSelection: courseListArr,
            subscriptionStartDate: formElementSelector.find(".popupInputDate").eq(0).val(),
            subscriptionEndDate: formElementSelector.find(".popupInputDate").eq(1).val(),
            ltiHashCode: "",
            licenseType: formElementSelector.find('.license-type').val(),
            numbersOfLicense: formElementSelector.find(".license-number").val(),
            licensePool: formElementSelector.find(".license-pool").val(),
            pilotDomain: formElementSelector.find(".pilot-domain").is(":checked"),
            pilotEndDate: formElementSelector.find(".pilot-end").eq(0).val()
        };

        domainRequestURI = "";
        if (requestType === "create") {
            domainRequestURI = self.model.get('_config').createDomain;
        } else if (requestType === "edit") {
            domainRequestURI = self.model.get('_config').editDomain + '/' + formElementSelector.attr("data-domain-id");
        }
        console.log(createdDomainObj)
        self.model.ajaxObjCall({ //1529
            type: "POST",
            url: domainRequestURI,
            contentType: "application/json; charset=utf-8",
            dataType: "json",
            data: JSON.stringify(createdDomainObj),
            headers: {
                "token": self.model.get('_sessionData').token
            }
        }, self.submitDomainFromData, self.domainFromError, void 0, {
            successVal: {}
        });
    };

    this.domainEditForm = function(formElementSelector) {
        self.cursorwait();
        self.updateCourseList();
        formElementSelector = $(formElementSelector);

        self.model.ajaxObjCall({ //1567
            url: self.model.get('_config').getDomainData + '/' + formElementSelector.attr("data-domain-id"),
        }, self.editDomainFromData, self.editDomainFromError, void 0, {
            successVal: {
                formElementSelector: formElementSelector
            },
            errorVal: {
                formElementSelector: formElementSelector
            }
        });
    };

    this.editnewfunction = function() {
        $('.editpopupBox').find(".course-list ul").empty();
        var courseSelectListHTML = '';
        $.each(self.model.get('_totalCourseArry'), function(courseIdx, course) {
            courseSelectListHTML += '<li><input type="checkbox" data-course-id="' + course.id + '" data-course-name="' + course.title + '" data-domain-id="' + course.domainid + '" />' + course.title + '</li>'
        });
        $('.editpopupBox').find(".course-list ul").html(courseSelectListHTML);

        $('.editpopupBox').find(".course-list ul li").each(function() {
            self.courseListEach($(this));
        });
    };

    this.courseListEach = function($element) {
        var courseListElId = $element.find('input').attr('data-course-id');
        var courseFound = false;
        $.each(self.model.get('_result').data.subscribedcourselist, function(subidx, subCourseId) {
            if (courseFound === false) {
                courseFound = self.eachsubscribedcourse(subidx, subCourseId, courseListElId, courseFound);
            }
        });
        if (courseFound) {
            $element.find('input').prop('checked', true);

        } else {
            $element.find('input').prop('checked', false);
        }
    };

    this.eachsubscribedcourse = function(subidx, subCourseId, courseListElId, courseFound) {
        if (courseListElId === subCourseId) {
            return true;
        } else {
            return false;
        }
    };

    this.addSchool = function(data_param, eqNo) {
        var localArray = [];
        self.model.ajaxObjCall({ //1656
            type: "GET",
            url: self.model.get('_config').DistrictList + '/' + data_param,
            crossDomain: true,
            contentType: "application/json"
        }, self.addSchoolData, self.commonError, void 0, {
            successVal: {
                eqNo: eqNo
            }
        });

    };

    this.schoolPopupData = function(dataParam) {
        self.model.ajaxObjCall({ //1697
            type: "GET",
            url: self.model.get('_config').getDomainData + '/' + dataParam,
            crossDomain: true,
            contentType: "application/json"
        }, self.schoolPopupDataCall, self.commonError, void 0, {
            successVal: {
                dataParam: dataParam
            }
        });
    };

    this.numberValidation = function(context) {
        var localData,
            localstrin,
            str,
            strN;
        localData = (context.val());
        localstrin = '';
        if (!$.isNumeric(localData)) {
            str = localData;
            str = parseInt(str.substring(0, str.length - 1));
            if (isNaN(str)) {
                str = '';
            }
            context.val((str));
            context.parent().find('.hidee:eq(1)').show();
            localstrin = (str);
        } else {
            localstrin = localData;
            context.parent().find('.hidee:eq(1)').hide();
        }

        if (context.attr('data-id') === "on") {
            if (localstrin > parseInt(self.model.get('_globalLicensecNum'), 10)) {
                strN = localstrin;
                strN = strN.substring(0, strN.length - 1);
                context.val(strN);
                context.next('label').next('label').next('label').show();
            } else {
                context.next('label').next('label').next('label').hide();
            }
        }

    };

    this.getCurrentDate = function() {
        var d,
            month,
            day,
            output;
        d = new Date();
        month = d.getMonth() + 1;
        day = d.getDate();
        output = (('' + month).length < 2 ? '0' : '') + month + '-' + (('' + day).length < 2 ? '0' : '') + day + '-' + d.getFullYear();
        return output;
    };

    //new methods sushant  312
    this.districtTabBoxData = function(res, parametersObj) {
        var thisContext,
            attrId,
            someDate,
            html,
            numberOfDaysToAdd,
            date,
            dateSplice,
            dateFormat,
            endDate,
            Month,
            Datee,
            newDate,
            subscriptionData;
        thisContext = parametersObj.thisContext;
        attrId = parametersObj.attrId;
        if (res.messageType === "SUCCESS") {
            someDate = new Date();
            html = '';
            numberOfDaysToAdd = 365;
            someDate.setDate(someDate.getDate() + numberOfDaysToAdd);
            date = new Date(someDate);
            dateFormat = date.toLocaleDateString().replace(/\//g, "-");
            dateSplice = dateFormat.split('-');
            Month = dateSplice[0];
            Datee = dateSplice[1];
            if (dateSplice[0].length === 1) {
                dateSplice[0] = [];
                dateSplice[0] = '0' + Month;
            }
            if (dateSplice[1].length === 1) {
                dateSplice[1] = [];
                dateSplice[1] = '0' + Datee;
            }
            newDate = dateSplice.join('-');
            endDate = res.data.customization.edivatelearn.subscriptionenddate === "" ? dateFormat : res.data.customization.edivatelearn.subscriptionenddate;
            subscriptionData = (res.data.customization.edivatelearn.fullsubscription) ? "Entire Catalog" : "Selective Courses";
            self.model.set('subscriptionstartdate', res.data.customization.edivatelearn.subscriptionstartdate);
            console.log(res.data.customization.edivatelearn.subscriptionstartdate);
            $('.norecords').remove();
            self.model.set('_globalLicensecNum', (res.data.customization.edivatelearn.nooflicense != null) ? res.data.customization.edivatelearn.nooflicense : 0);
            html += '<div class="districtHeadDetails"><div class="LPSDContainer">';
            html += '<div class="LPSDTopTableBox"> <table class="LPSDTopTable"> <tbody><tr> <td style="width:8%;">Name: </td><td style="width:12%;">' + res.data.domain.name + '</td><td style="width:10%;">Subscription Type: </td><td style="width:16%;">' + subscriptionData + '</td><td style="width:10%;">Number of License:</td><td style="width:10%;">' + res.data.customization.edivatelearn.nooflicense + '</td></tr><tr> <td>External Id: </td><td>' + res.data.domain.reference + '</td><td>Subscription Date:</td><td>' + res.data.customization.edivatelearn.subscriptionstartdate + ' ' + 'To' + ' ' + endDate + '</td><td>Pilot End Date:</td><td>' + res.data.customization.edivatelearn.pilotenddate + '</td></tr><tr> <td>Login Prefix: </td><td>' + res.data.domain.userspace + '</td><td>License Type:</td><td>' + res.data.customization.edivatelearn.licensetype + '</td><td></td><td></td></tr></tbody></table> </div> <div class="LPSDAvlList"> <div class="SASHead"><hgroup><h1>School available in ' + res.data.domain.name + '</h1></hgroup></div><div class="SAList"> <table class="SAListTable"> <tbody><tr> <th>School Domain Name</th> <th>External id</th> <th>Login Prefix</th> <th>License Type</th> <th>License Pool</th> <th>Number of License</th> <th>Action</th> </tr>';

            self.model.ajaxObjCall({ //312
                type: "GET",
                url: self.model.get('_config').schoolList + '/' + attrId + '?querystring=0&limit=' + self.model.get('_rowView'),
                crossDomain: true,
                contentType: "application/json"
            }, self.schoolListData, self.commonError, void 0, {
                successVal: {
                    thisContext: thisContext,
                    html: html
                }
            });

        } else if (data.messageType === "ERROR") {
            $('.norecords,.districtHeadDetails').remove();
            self.cursordefault();
            html = '';
            html += '<div class="districtHeadDetails">';

            html += '<label class="norecords">No School Found</label>';

            html += "</div>";

            thisContext.parents('.districtTabBox').append(html);
        }
    };

    this.schoolListData = function(data, parametersObj) {
        var thisContext,
            html,
            i;
        thisContext = parametersObj.thisContext;
        html = parametersObj.html;
        if (data.messageType === "SUCCESS") {
            self.model.set('_schoolRowDisableCheck', data.data.domains.length);
            for (i = 0; i < data.data.domains.length; i++) {
                html += '<tr data-id="' + data.data.domains[i].id + '"> <td>' + data.data.domains[i].name + '</td><td>' + data.data.domains[i].reference + '</td><td>' + data.data.domains[i].userspace + '</td><td>' + data.data.domains[i].data.customization.edivatelearn.licensetype + '</td><td>' + data.data.domains[i].data.customization.edivatelearn.pooltype + '</td><td>' + data.data.domains[i].data.customization.edivatelearn.nooflicense + '</td><td><button class="editSchoolpop"><i class="fa fa-pencil"></i>Edit</button></td></tr>';
            }
            html += '</tbody></table> </div><div class="FootPagination"><div class="floatLeft dataleft"> Showing <span class="demo"> <select class="balck showEntry"> <option value="1">1</option> <option value="2">2 </option> <option value="3">3</option> <option value="4" selected>4</option> <option value="5">5</option> </select> </span>  </div><div class="dataright"> <nav> <ul class="pagination"> <li class="disabled"> <button class="PreviousCir"  aria-label="Previous"> <i class="fa fa-play LSArrow"></i> </button> </li><li> <button class="NextCir"  aria-label="Next"> <i class="fa fa-play RSArrow"></i> </button> </li></ul> </nav> </div></div></div></div></div>';
            thisContext.parents('.districtTabBox').append(html);
            self.bindevent();
            self.cursordefault();
            $('.PreviousCir').prop('disabled', true).addClass('disabled');

        } else if (data.messageType === "ERROR") {
            $('.norecords').remove();
            self.cursordefault();
            html += '<label class="norecords">No School Found</label></div>';
            thisContext.parents('.districtTabBox').append(html);
            thisContext.parents('.districtTabBox').find('.LPSDAvlList').html('<label class="norecords">No School Found</label>');
        }
        if (thisContext.parents('.districtTabBoxHead').next().is(':visible')) {
            $('.districtHeadDetails').slideUp('slow', function() {
                setTimeout(function() {
                    $('.districtHeadDetails').remove();
                }, 350);
            });
        } else {
            $('.districtHeadDetails').slideUp('slow');
            thisContext.parents('.districtTabBoxHead').next().slideDown('slow');
        }

    };

    //399
    this.displayEntry = function(res, parametersObj) {
        var html = parametersObj.html,
            i;
        if (res.messageType === "SUCCESS") {
            self.model.set('_schoolRowDisableCheck', res.data.domains.length);
            $('.SASHead,.SAList').remove();
            html += '<div class="SASHead"><hgroup><h1>School available in ' + $(this).parents('.districtTabBox').find('.disTrictList').text() + '</h1></hgroup></div><div class="SAList"> <table class="SAListTable"> <tbody><tr> <th>School Domain Name</th> <th>External id</th> <th>Login Prefix</th> <th>License Type</th> <th>License Pool</th> <th>Number of License</th> <th>Action</th> </tr>';

            for (i = 0; i < res.data.domains.length; i++) {
                html += '<tr data-id="' + res.data.domains[i].id + '"> <td>' + res.data.domains[i].name + '</td><td>' + res.data.domains[i].reference + '</td><td>' + res.data.domains[i].userspace + '</td><td>' + res.data.domains[i].data.customization.edivatelearn.licensetype + '</td><td>' + res.data.domains[i].data.customization.edivatelearn.pooltype + '</td><td>' + res.data.domains[i].data.customization.edivatelearn.nooflicense + '</td><td><button class="editSchoolpop"><i class="fa fa-pencil"></i>Edit</button></td></tr>';
            }
            html += '</tbody></table></div>';
            self.model.get('_globalThisContext').parents('.districtTabBox').find('.LPSDAvlList').prepend(html);
            self.bindevent();
            self.cursordefault();
        } else if (res.messageType === "ERROR") {
            $('.norecords').remove();
            self.cursordefault();
            html = '';
            html += '<div class="districtHeadDetails"><ul class="schoolView">';

            html += '<li>No School Found</li>';

            html += "</ul></div>";

            self.model.get('_globalThisContext').parents('.districtTabBox').append(html);
        }
    };

    //459   //514
    this.displayNextSchoolEntry = function(res, parametersObj) {
        var html = parametersObj.html,
            i;
        if (res.messageType === "SUCCESS") {
            self.model.set('_schoolRowDisableCheck', res.data.domains.length);
            html += ' <tbody><tr> <th>School Domain Name</th> <th>External id</th> <th>Login Prefix</th> <th>License Type</th> <th>License Pool</th> <th>Number of License</th> <th>Action</th> </tr>';

            for (i = 0; i < res.data.domains.length; i++) {
                html += '<tr data-id="' + res.data.domains[i].id + '"> <td>' + res.data.domains[i].name + '</td><td>' + res.data.domains[i].reference + '</td><td>' + res.data.domains[i].userspace + '</td><td>' + res.data.domains[i].data.customization.edivatelearn.licensetype + '</td><td>' + res.data.domains[i].data.customization.edivatelearn.pooltype + '</td><td>' + res.data.domains[i].data.customization.edivatelearn.nooflicense + '</td><td><button class="editSchoolpop"><i class="fa fa-pencil"></i>Edit</button></td></tr>';
            }
            html += '</tbody>';
            self.model.get('_globalThisContext').parents('.districtTabBox').find('.SAListTable').html(html);
            self.bindevent();
            self.cursordefault();
        } else if (res.messageType === "ERROR") {
            $('.norecords').remove();
            self.cursordefault();
            html = '';
            html += '<div class="districtHeadDetails"><ul class="schoolView">';

            html += '<li>No School Found</li>';

            html += "</ul></div>";

            self.model.get('_globalThisContext').parents('.districtTabBox').append(html);
        }
    };

    //572    //630
    this.displayNextDomain = function(data, parametersObj) {
        var html = parametersObj.html,
            i;
        if (data.messageType === "SUCCESS") {
            self.cursordefault();
            $('.districtTabBox').remove();
            html = '<ul>';
            self.model.set('_nextdisbleChcek', data.data.domains.length);
            for (i = 0; i < data.data.domains.length; i++) {
                html += '<li id="' + data.data.domains[i].id + '"><section class="districtTabBox"><div class="districtTabBoxHead"><hgroup><h1 data-id="' + data.data.domains[i].id + '" class="floatLeft disTrictList">' + data.data.domains[i].name + '</h1></hgroup><p class="floatRight"><ul class="schoolBttns floatRight"> <li id="snwEdit" class="edit"><i class="fa fa-pencil"></i><a id="snwEdit_Link" href="#">Edit</a></li><li id="snwaddschool" class="addschool"><i class="fa fa-plus"></i><a id="snwAdd_School" href="#">Add School</a></li></ul></p><div class="floatRight"><div class="numSchools"><ul class="numSchoolsDet"><li></li><li></li></ul></div></div></div></section>';
                html += '<div class="districtHeadDetails"><div class="LPSDContainer"><div class="LPSDTopTableBox"> <table class="LPSDTopTable"> <tbody><tr> <td style="width:8%;">Name: </td><td style="width:12%;">Sample District 1</td><td style="width:10%;">Subscription Type: </td><td style="width:16%;">Entire Catalog</td><td style="width:10%;">Number of License:</td><td style="width:10%;">100</td></tr><tr> <td>External Id: </td><td>21536</td><td>Subscription Date:</td><td>07/09/2015 to 07/09/2016</td><td>Pilot End Date:</td><td>07/09/2016</td></tr><tr> <td>Login Prefix: </td><td>lm-test1</td><td>License Type:</td><td>Pre Seat</td><td></td><td></td></tr></tbody></table> </div> </div>';
                html += '</li>'
            }
            html += '</ul>';
            $('#adminContainer .domainContainer').html(html);
            if (data.data.domains.length >= 4) {
                $('.dummydata').css('height', '10px');
            } else {
                $('.dummydata').css('height', '222px');
            }
        } else {
            self.cursordefault();
            return false;
        }
    };

    this.nextDomainCallComplete = function() {
        setTimeout(function() {
            self.bindevent();
        }, 400)
    };

    //685
    this.domainShowEntry = function(data, parametersObj) {
        var html = parametersObj.html,
            i,
            length,
            t;
        if (data.messageType === "SUCCESS") {
            self.cursordefault();
            self.model.set('_nextdisbleChcek', data.data.domains.length);
            $('.districtTabBox').remove();
            html = '<ul>';
            for (i = 0; i < data.data.domains.length; i++) {

                //                  schlId=data.data.domains.id;
                html += '<li id="' + data.data.domains[i].id + '"><section class="districtTabBox"><div class="districtTabBoxHead"><hgroup><h1 data-id="' + data.data.domains[i].id + '" class="floatLeft disTrictList">' + data.data.domains[i].name + '</h1></hgroup><p class="floatRight"><ul class="schoolBttns floatRight"> <li id="snwEdit" class="edit"><i class="fa fa-pencil"></i><a id="snwEdit_Link" href="#">Edit</a></li><li id="snwaddschool" class="addschool"><i class="fa fa-plus"></i><a id="snwAdd_School" href="#">Add School</a></li></ul></p><div class="floatRight"><div class="numSchools"><ul class="numSchoolsDet"><li></li><li></li></ul></div></div></div></section>';

                html += '<div class="districtHeadDetails"><div class="LPSDContainer"><div class="LPSDTopTableBox"> <table class="LPSDTopTable"> <tbody><tr> <td style="width:8%;">Name: </td><td style="width:12%;">Sample District 1</td><td style="width:10%;">Subscription Type: </td><td style="width:16%;">Entire Catalog</td><td style="width:10%;">Number of License:</td><td style="width:10%;">100</td></tr><tr> <td>External Id: </td><td>21536</td><td>Subscription Date:</td><td>07/09/2015 to 07/09/2016</td><td>Pilot End Date:</td><td>07/09/2016</td></tr><tr> <td>Login Prefix: </td><td>lm-test1</td><td>License Type:</td><td>Pre Seat</td><td></td><td></td></tr></tbody></table> </div> </div>';
                html += '</li>'
            }
            html += '</ul>';
            $('#adminContainer .domainContainer').html(html);
            $('.FootPagination').show();
        } else {
            length = $('.domainContainer>ul>li').length;
            self.cursordefault();
            for (t = 0; t < length - 1; t++) {
                $('.domainContainer>ul>li').eq(0).remove();
            }
            $('.districtTabBox').html('<label class="norecords" style="">No District Found</label>');
            $('.FootPagination').hide();
        }
    };

    ////809
    this.searchDataDisplay = function(res, parametersObj) {
        var html = parametersObj.html,
            schoolId,
            i;
        if (res.messageType === "SUCCESS") {
            schoolId = res.data.domains[0].id;
            self.cursordefault();
            $('.districtTabBox').remove();
            html = '<ul>';
            self.model.set('_nextdisbleChcek', res.data.domains.length);
            for (i = 0; i < res.data.domains.length; i++) {
                html += '<li id="' + res.data.domains[i].id + '" ><section class="districtTabBox"><div class="districtTabBoxHead"><hgroup><h1 data-id="' + res.data.domains[i].id + '" class="floatLeft disTrictList">' + res.data.domains[i].name + '</h1></hgroup><p class="floatRight"><ul class="schoolBttns floatRight"> <li id="snwaddschool" class="addschool"><i class="fa fa-plus"></i><a id="snwAdd_School" href="#">Add School</a></li><li id="snwEdit" class="edit"><i class="fa fa-pencil"></i><a id="snwEdit_Link" href="#">Edit</a></li></ul></p><div class="floatRight"><div class="numSchools"><ul class="numSchoolsDet"><li></li><li></li></ul></div></div></section>';
                html += '<div class="districtHeadDetails"><div class="LPSDContainer"><div class="LPSDTopTableBox"> <table class="LPSDTopTable"> <tbody><tr> <td style="width:8%;">Name: </td><td style="width:12%;">Sample District 1</td><td style="width:10%;">Subscription Type: </td><td style="width:16%;">Entire Catalog</td><td style="width:10%;">Number of License:</td><td style="width:10%;">100</td></tr><tr> <td>External Id: </td><td>21536</td><td>Subscription Date:</td><td>07/09/2015 to 07/09/2016</td><td>Pilot End Date:</td><td>07/09/2016</td></tr><tr> <td>Login Prefix: </td><td>lm-test1</td><td>License Type:</td><td>Pre Seat</td><td></td><td></td></tr></tbody></table> </div> </div>';
                html += '</li>'
            }
            html += "</ul>";
            $('#adminContainer .domainContainer').html(html);
            if (res.data.domains.length >= 4) {
                $('.dummydata').css('height', '10px');
            } else {
                $('.dummydata').css('height', '222px');
            }
            self.bindevent();
            $('.FootPagination').show();
        } else {
            $('.districtTabBox,.norecords').remove();
            self.cursordefault();
            html = '';
            html += '<section class="districtTabBox"><div class="districtTabBoxHead"><div class="districtHeadDetails" style="display:block;"><ul class="schoolView">';
            html += '<li>No District Found</li>';
            html += '</ul></div></div><section class="districtTabBox">';
            $('#adminContainer .domainContainer').html(html);
            $('.FootPagination').hide();
        }
    };

    ////1060
    this.editSchoolData = function(rslt, parametersObj) {
        var stateSelctedVal = parametersObj.stateSelctedVal,
            editScholPrntId,
            fromselector = parametersObj.fromselector;
        $('.editschlpopupBox,.popGrayBg').show();
        if (rslt.messageType === "SUCCESS") {
            editScholPrntId = rslt.data.domain.parentid;
            fromselector.find('.schldomainname').val(rslt.data.domain.name);
            fromselector.find('.loginprefix').val(rslt.data.domain.userspace);
            fromselector.find('.externalid').val(rslt.data.domain.reference);
            fromselector.find('.licensetype').val(rslt.data.customization.edivatelearn.licensetype);
            fromselector.find('.license-number').val(rslt.data.customization.edivatelearn.nooflicense);
            fromselector.find('.license-pool').val(rslt.data.customization.edivatelearn.pooltype);
            fromselector.find('.popupInputDate').val(rslt.data.customization.edivatelearn.pilotenddate);
            if (rslt.data.customization.edivatelearn.pooltype === 'Fixed') {
                $('#snw_License1').removeAttr('disabled');
            } else {
                $('#snw_License1').attr('disabled', "disabled");
            }
            if (rslt.data.customization.edivatelearn.pilot) {
                fromselector.find('.pilot-domain').prop('checked', true);
            } else {
                fromselector.find('.pilot-domain').prop('checked', false);
            }
            self.model.ajaxObjCall({
                type: "GET",
                url: self.model.get('_config').DistrictList + '/' + stateSelctedVal,
            }, self.editSchoolDistrictData, self.commonError, void 0, {
                successVal: {
                    stateSelctedVal: stateSelctedVal,
                    fromselector: fromselector,
                    rslt: rslt,
                    editScholPrntId: editScholPrntId
                }
            });
        } else {
            self.cursordefault();
        }
    };

    this.editSchoolDistrictData = function(rslte, parametersObj) {
        var fromselector = parametersObj.fromselector,
            rslt = parametersObj.rslt,
            html,
            i,
            editScholPrntId = parametersObj.editScholPrntId;
        if (rslt.messageType === "SUCCESS") {
            html = '';
            fromselector.find('.schooldistrictlist').html(function() {
                for (i = 0; i < rslte.data.domains.length; i++) {
                    html += '<option value="' + rslte.data.domains[i].id + '">' + rslte.data.domains[i].name + '</option>';
                }
                return html;
            }).promise().done(function() {
                fromselector.find('.schooldistrictlist').val(editScholPrntId);
            });
            self.cursordefault();
        }
        self.cursordefault();
    };

    ////1142
    this.saveSchoolData = function(rslt, parametersObj) {
        var stateSelctedVal = parametersObj.stateSelctedVal,
            fromselector = parametersObj.fromselector;
        if (rslt.messageType === "SUCCESS") {
            alert(rslt.message)
            $('.grayBg,.popGrayBg').hide();
            $('.addschlpopupBox,.editschlpopupBox').hide();
            self.cursordefault();
            self.model.get('_districtContext').trigger('click');
            self.model.get('_districtContext').parents('.districtTabBoxHead').next().show();
        } else if (rslt.messageType === "ERROR") {
            alert(rslt.message);
            self.cursordefault();
        } else {
            alert(rslt.messageType);
            $('.grayBg,.popGrayBg').hide();
            $('.addschlpopupBox,.editschlpopupBox').hide();
            self.cursordefault();
            self.model.get('_districtContext').trigger('click');
            self.model.get('_districtContext').parents('.districtTabBoxHead').next().show();
        }
    };

    ////1182
    this.logout = function(rslt, parametersObj) {
        window.location.href = "index.html";
    };

    //1227
    this.districtOptionLoading = function(res, parametersObj) {
        var split_data = parametersObj.split_data,
            html = parametersObj.html,
            state_list = parametersObj.state_list,
            urlStore = parametersObj.urlStore

        if (res.messageType === "SUCCESS") {
            $('.dummydata').html();
            $('.statename').html(function() {
                $.each(res.data.domains, function(i, v) {
                    split_data = v.userspace.split('-');
                    state_list += '<option value=' + v.id + '>' + split_data[1].toUpperCase() + ' ' + v.name + '</option>';
                    self.model.get('_stateId').push(v.id);
                    self.model.get('_stateName').push(split_data[1].toUpperCase() + ' ' + v.name);
                });
                return state_list;
            });
            self.model.set('_postId', res.data.domains[0].id);
            self.model.set('_optionselector', $('.statename option:first').val());
            self.model.ajaxObjCall({ //1182

                type: "GET",
                url: urlStore + '/' + res.data.domains[0].id + '?querystring=0&limit=' + self.model.get('_dropdownCount') + '',
                crossDomain: true,
                contentType: "application/json"
            }, self.districtDataLoading, self.commonError, void 0, {
                successVal: {
                    html: html

                }
            })
            self.statelistchange();
        } else {
            self.cursordefault();
            $('.norecords').remove();
            $('.dummydata').html('<label class="norecords" style="">No Record Found</label>');
        }
    };

    this.districtDataLoading = function(data, parametersObj) {
        var html = parametersObj.html,
            schoolId,
            i,
            t;
        if (data.messageType === "SUCCESS") {
            schoolId = data.data.domains[0].id;
            self.cursordefault();
            $('.districtTabBox').remove();
            html = '<ul>';
            self.model.set('_nextdisbleChcek', data.data.domains.length);
            for (i = 0; i < data.data.domains.length; i++) {
                html += '<li id="' + data.data.domains[i].id + '" ><section class="districtTabBox"><div class="districtTabBoxHead"><hgroup><h1 data-id="' + data.data.domains[i].id + '" class="floatLeft disTrictList">' + data.data.domains[i].name + '</h1></hgroup><p class="floatRight"><ul class="schoolBttns floatRight"> <li id="snwEdit" class="edit"><i class="fa fa-pencil"></i><a id="snwEdit_Link" href="#">Edit</a></li><li id="snwaddschool" class="addschool"><i class="fa fa-plus"></i><a id="snwAdd_School" href="#">Add School</a></li></ul></p><div class="floatRight"><div class="numSchools"><ul class="numSchoolsDet"><li></li><li></li></ul></div></div></section>';

                html += '<div class="districtHeadDetails"><div class="LPSDContainer"><div class="LPSDTopTableBox"> <table class="LPSDTopTable"> <tbody><tr> <td style="width:8%;">Name: </td><td style="width:12%;">Sample District 1</td><td style="width:10%;">Subscription Type: </td><td style="width:16%;">Entire Catalog</td><td style="width:10%;">Number of License:</td><td style="width:10%;">100</td></tr><tr> <td>External Id: </td><td>21536</td><td>Subscription Date:</td><td>07/09/2015 to 07/09/2016</td><td>Pilot End Date:</td><td>07/09/2016</td></tr><tr> <td>Login Prefix: </td><td>lm-test1</td><td>License Type:</td><td>Pre Seat</td><td></td><td></td></tr></tbody></table> </div> </div>';

                html += '</li>';
            }
            html += "</ul>";
            $('#adminContainer .domainContainer').html(html);
            if (data.data.domains.length >= 4) {
                $('.dummydata').css('height', '10px');
            } else {
                $('.dummydata').css('height', '222px');
            }
            self.sortingDistrict();
            self.bindevent();
            $('.FootPagination').show();
        } else {
            self.cursordefault();
            for (t = 0; t < ($('.domainContainer>ul>li').length); t++) {
                $('.domainContainer>ul>li').eq(0).remove();
            }
            $('.norecords').remove();
            $('.districtTabBox').html('<label class="norecords" style="">No District Found</label>');
            $('.FootPagination').hide();
        }
    };

    ////1317
    this.districtOptionLoadingData = function(data, parametersObj) {
        var split_data = parametersObj.split_data,
            i,
            html,
            length,
            t,
            state_list = parametersObj.state_list;
        if (data.messageType === "SUCCESS") {
            self.model.set('_nextdisbleChcek', data.data.domains.length);
            self.cursordefault();
            $('.districtTabBox').remove();
            html = '<ul>';
            for (i = 0; i < data.data.domains.length; i++) {

                //                  schlId=data.data.domains.id;
                html += '<li id="' + data.data.domains[i].id + '"><section class="districtTabBox"><div class="districtTabBoxHead"><hgroup><h1 data-id="' + data.data.domains[i].id + '" class="floatLeft disTrictList">' + data.data.domains[i].name + '</h1></hgroup><p class="floatRight"><ul class="schoolBttns floatRight"> <li id="snwEdit" class="edit"><i class="fa fa-pencil"></i><a id="snwEdit_Link" href="#">Edit</a></li><li id="snwaddschool" class="addschool"><i class="fa fa-plus"></i><a id="snwAdd_School" href="#">Add School</a></li></ul></p><div class="floatRight"><div class="numSchools"><ul class="numSchoolsDet"><li></li><li></li></ul></div></div></div></section>';

                html += '<div class="districtHeadDetails"><div class="LPSDContainer"><div class="LPSDTopTableBox"> <table class="LPSDTopTable"> <tbody><tr> <td style="width:8%;">Name: </td><td style="width:12%;">Sample District 1</td><td style="width:10%;">Subscription Type: </td><td style="width:16%;">Entire Catalog</td><td style="width:10%;">Number of License:</td><td style="width:10%;">100</td></tr><tr> <td>External Id: </td><td>21536</td><td>Subscription Date:</td><td>07/09/2015 to 07/09/2016</td><td>Pilot End Date:</td><td>07/09/2016</td></tr><tr> <td>Login Prefix: </td><td>lm-test1</td><td>License Type:</td><td>Pre Seat</td><td></td><td></td></tr></tbody></table> </div> </div>';
                html += '</li>'
            }
            html += '</ul>';
            $('#adminContainer .domainContainer').html(html);
            $('.DomainPrevious').prop('disabled', true).addClass('disabled');
            $('.DomainNext').prop('disabled', false).removeClass('disabled');

            if (data.data.domains.length >= 4) {
                $('.dummydata').css('height', '10px');
            } else {
                $('.dummydata').css('height', '222px');
            }
            $('.FootPagination').show();
            self.sortingDistrict();
        } else {
            length = $('.domainContainer>ul>li').length;
            self.cursordefault();
            for (t = 0; t < length - 1; t++) {
                $('.domainContainer>ul>li').eq(0).remove();
            }
            $('.norecords').remove();
            $('.districtTabBox').html('<label class="norecords" style="">No District Found</label>');
            $('.FootPagination').hide();
        }
    };

    ////1447
    this.updateCourseData = function(res, parametersObj) {
        var selectedCourseCatalog = parametersObj.selectedCourseCatalog,
            state_list = parametersObj.state_list,
            ind = parametersObj.ind,
            totalCourses = [],
            courseSelectListHTML,
            totalCourses = totalCourses.concat(res.data.domain);
        courseSelectListHTML = "";
        if (totalCourses.length) {
            if (ind === selectedCourseCatalog.length - 1) {
                if ($(".entire-catalog").is(":checked")) {
                    $.each(totalCourses, function(index, course) {
                        courseSelectListHTML += '<li><input type="checkbox" data-course-id="' + course.id + '" data-course-name="' + course.title + '" data-domain-id="' + course.domainid + '" checked="true"/>' + course.title + '</li>';
                    });
                    $(".course-list ul").html(courseSelectListHTML);
                } else {
                    $.each(totalCourses, function(index, course) {
                        courseSelectListHTML += '<li><input type="checkbox" data-course-id="' + course.id + '" data-course-name="' + course.title + '" data-domain-id="' + course.domainid + '"/>' + course.title + '</li>';
                    });
                    $(".corseselectList ul").html(courseSelectListHTML);
                }
            }
            self.bindevent();
        } else {
            courseSelectListHTML = '<li>Please select a Course Catalog first.</li>';
            $(".corseselectList ul").html(courseSelectListHTML);
        }
    };

    //1529
    this.submitDomainFromData = function(res, parametersObj) {
        var selectedCourseCatalog = parametersObj.selectedCourseCatalog,
            state_list = parametersObj.state_list;
        self.cursordefault();
        if (res.messageType === "SUCCESS") {
            alert(res.message);
            $('.statename').trigger('change');
            $('.grayBg').hide();
            $('.popGrayBg').hide();
            $('.popupBox,.editpopupBox').hide();
        } else {
            alert(res.message);
        }
        self.cursordefault();
    };

    this.domainFromError = function(err) {
        self.cursordefault();
        alert('Encountered an error while createdistrict');
        console.log("Encountered an error while createdistrict", err);
        $('.grayBg').hide();
        $('.popupBox,.editpopupBox').hide();
    };

    //1567
    this.editDomainFromData = function(res, parametersObj) {
        var formElementSelector = parametersObj.formElementSelector,
            totalCourses,
            courseListURI,
            state_list = parametersObj.state_list;
        self.cursorwait();
        self.model.set('_result', res);
        formElementSelector.find('select.editdomainlist').val(res.data.domain.parentid);
        formElementSelector.find('select.editdomainlist').attr('value', res.data.domain.parentid);
        //                formElementSelector.find('input.domain-name').val(res.data.domain.name);
        formElementSelector.find('input.login-prefix').val(res.data.domain.userspace).attr('disabled', 'disabled');
        formElementSelector.find('input.login-prefix').attr('value', res.data.domain.userspace);
        formElementSelector.find('input.external-id').val(res.data.domain.reference);
        formElementSelector.find('input.external-id').attr('value', res.data.domain.reference);
        formElementSelector.find('select.license-type').val(res.data.customization.edivatelearn.licensetype);
        formElementSelector.find('select.license-type').attr('value', res.data.customization.edivatelearn.licensetype);
        formElementSelector.find('select.license-pool').val(res.data.customization.edivatelearn.pooltype);
        formElementSelector.find('select.license-pool').attr('value', res.data.customization.edivatelearn.pooltype);
        formElementSelector.find('input.license-number').val(res.data.customization.edivatelearn.nooflicense);
        formElementSelector.find('input.license-number').attr('value', res.data.customization.edivatelearn.nooflicense);
        formElementSelector.find('input.pilot-end').val(res.data.customization.edivatelearn.pilotenddate);
        formElementSelector.find('input.pilot-end').attr('value', res.data.customization.edivatelearn.pilotenddate);
        formElementSelector.find('#snwSubscription-start').val(res.data.customization.edivatelearn.subscriptionstartdate);
        formElementSelector.find('#snwSubscription-start').attr('value', res.data.customization.edivatelearn.subscriptionstartdate);
        formElementSelector.find('#snwSubscription-end').val(res.data.customization.edivatelearn.subscriptionenddate);
        formElementSelector.find('#snwSubscription-end').attr('value', res.data.customization.edivatelearn.subscriptionenddate);
        if (res.data.customization.pilot) {
            formElementSelector.find('input.pilot').attr("checked", "true");
        } else {
            formElementSelector.find('input.pilot').attr("checked", "false");
        }
        if (res.data.customization.edivatelearn.fullsubscription) {
            formElementSelector.find('#snwEntire_Catalog').prop("checked", true);
            formElementSelector.find('#snwselective-course').prop("checked", false);
        } else {
            formElementSelector.find('#snwEntire_Catalog').prop("checked", false);
            formElementSelector.find('#snwselective-course').prop("checked", true);
        }
        //                if($('#snwSelectInput').)
        totalCourses = [];
        $('.editpopupBox .corsecatalogList').find('li').each(function(i) {
            //                    alert($(this).find('input').attr('data-domain-id')===res.data.subscribedproviderlist[i]);
            //                    alert($(this).find('input').attr('data-domain-id')+'()'+res.data.subscribedproviderlist[i])
            if (res.data.subscribedproviderlist == null) {
                self.cursordefault();
                $('.popGrayBg').show();
                self.bindevent();
            } else {
                //if($(this).find('input').attr('data-domain-id')===res.data.subscribedproviderlist[i]){
                if (res.data.subscribedproviderlist.indexOf($(this).find('input').attr('data-domain-id')) != -1) {
                    $(this).find('input').prop("checked", true);
                    courseListURI = self.model.get('_config').courseList + "/" + $(this).find('input').attr('data-domain-id');
                    $('#example' + $(this).find('input').attr('data-domain-id')).attr('checked', true);
                    self.model.ajaxObjCall({ //1567
                        url: courseListURI
                    }, self.courseListData, self.commonError, void 0, {
                        successVal: {
                            res: res
                        }
                    });
                } else {
                    self.cursordefault();
                    $('.popGrayBg').show();
                    self.bindevent();
                }
            }
        });
        $('.editpopupBox').find(".course-list ul input").attr('checked', 'checked');
    };

    this.editDomainFromError = function(err) {
        var formElementSelector = parametersObj.formElementSelector;
        self.cursordefault();
        alert("Error fetching domain data for", formElementSelector.data("domain-id"));
    };

    this.courseListData = function(data, parametersObj) {
        var courseSelectListHTML = '',
            res = parametersObj.res,
            totalCourses = [];
        totalCourses = totalCourses.concat(data.data.domain);
        //TODO  

        self.model.set('_totalCourseArry', totalCourses);
        $.each(totalCourses, function(index, course) {
            if (res.data.subscribedcourselist[index] === course.id) {
                courseSelectListHTML += '<li><input type="checkbox" data-course-id="' + course.id + '" data-course-name="' + course.title + '" data-domain-id="' + course.domainid + '" checked="true"/>' + course.title + '</li>';
            } else {
                courseSelectListHTML += '<li><input type="checkbox" data-course-id="' + course.id + '" data-course-name="' + course.title + '" data-domain-id="' + course.domainid + '" checked="true"/>' + course.title + '</li>';
            }
        });
        $('.editpopupBox').find(".course-list ul").html(courseSelectListHTML);
        self.cursordefault();
        $('.popGrayBg').show();
        self.bindevent();
    };

    //1656
    this.addSchoolData = function(res, parametersObj) {
        var eqNo = parametersObj.eqNo,
            i,
            state_list = parametersObj.state_list;
        state_list = '';
        if (res.messageType === "SUCCESS") {

            $('#snw_schooldistrictlist1');

            for (i = 0; i < res.data.domains.length; i++) {
                state_list += '<option value=' + res.data.domains[i].id + '>' + res.data.domains[i].name + '</option>';
            }
            $('#snw_schooldistrictlist').html(state_list);
            $('#snw_schooldistrictlist1').html(state_list);
            if (eqNo == 0) {
                $('.editschlpopupBox .schooldistrictlist').val(res.data.domains[0].id);
                $('.addschlpopupBox .schooldistrictlist').val(res.data.domains[0].id);
                self.schoolPopupData(res.data.domains[0].id);
            } else {
                $('.addschlpopupBox .schooldistrictlist').val(eqNo);
                self.schoolPopupData(eqNo);
            }
        } else {
            $('.norecords').remove();
            self.cursordefault();
            $('.schooldistrictlist').html('<option class="norecords" style="">No Record Found</option>');
        }
    };

    //1697
    this.schoolPopupDataCall = function(res, parametersObj) {
        var dataParam = parametersObj.dataParam,
            state_list = parametersObj.state_list;
        console.log(res)
        state_list = '';
        $('.editschlpopupBox .schooldistrictlist').val(dataParam);
        $('.addschlpopupBox .schooldistrictlist').val(dataParam);
        if (res.messageType === "SUCCESS") {
            self.model.set('_globalLicensecNum', (res.data.customization.edivatelearn.nooflicense != null) ? res.data.customization.edivatelearn.nooflicense : 0)
            $('.editschlpopupBox .licensetype').val(res.data.customization.edivatelearn.licensetype);
            $('.addschlpopupBox .licensetype').val(res.data.customization.edivatelearn.licensetype);
            if (res.data.customization.edivatelearn.pooltype === 'Fixed') {
                $('#snw_License').val('').removeAttr('disabled');
                $('#snw_License1').val('').removeAttr('disabled');
            } else {
                $('#snw_License').val('').attr('disabled', "disabled");
                $('#snw_License1').val('').attr('disabled', "disabled");
            }
            $('#snw_balck_data').val(res.data.customization.edivatelearn.pooltype);
            $('#snw_balck_data1').val(res.data.customization.edivatelearn.pooltype);
            $('#snw_popupInputDate').val(res.data.customization.edivatelearn.pilotenddate);
            if (res.data.customization.edivatelearn.pilot) {
                $('#snw_checkbox').attr("checked", "true");
                $('#snw_checkbox_edit').attr("checked", "true");
            } else {
                $('#snw_checkbox').attr("checked", "false");
                $('#snw_checkbox_edit').attr("checked", "false");
            }
            self.cursordefault();
            $('.popGrayBg').show();
        }
    };

    //NKN
    this.getProviderListDataCall = function(res, parametersObj) {
        var dataParam = parametersObj.dataParam,
            t,
            html,
            state_list = parametersObj.state_list;
        html = '';
        console.log(res)
        state_list = '';
        $('.popupBox .inputTracker').empty();
        $('.editpopupBox').find('.inputTrackerEdit').empty();
        if (res.messageType) {
            for (t = 0; t < res.data.domains.length; t++) {
                html += '<li><input id="checkbox' + t + '" type="checkBox" data-domain-id="' + res.data.domains[t].id + '" data-course-list=""/>' + res.data.domains[t].name + '</li>';
                if (res.data.domains[t].id != 34816208) {
                    $('.popupBox .inputTracker').append('<input type="checkbox" disabled="disabled" value="" name="Add Subdomain" id="demo' + res.data.domains[t].id + '" class="demo">' + res.data.domains[t].name + '');
                    $('.editpopupBox .inputTrackerEdit').append('<input type="checkbox" disabled="disabled" value="" name="Add Subdomain" id="example' + res.data.domains[t].id + '" class="example">' + res.data.domains[t].name + '');
                }
            }
            $('.corsecatalogList ul').html(html);
            self.cursordefault();
            self.bindevent();
        } else {
            $('.corsecatalogList ul').html('<li><input type="checkBox"/>No Data Found</li>');
            self.cursordefault();
        }
        self.bindevent();
        $('.popGrayBg').show();
        if ($(self.model.get('_editbutton')).hasClass('edit')) {
            self.domainEditForm(self.model.get('_editbox'));
        }
    };

    this.commonError = function() {
        alert('error loading page');
    };

    //Handler functions
    this.circlePlusHandler = function() {
        $('.grayBg').css({
            'width': self.model.get('win_width'),
            'height': self.model.set('win_height')
        }).show();
        $('.addNew').show();
    };

    this.loginKeyUpPrefix = function($element) {
        var loginPrefx = $.trim($element.val()),
            loginArray = loginPrefx.split('-'),
            loginUndifndChk = loginArray[1] != undefined ? loginArray[1] : '',
            htmlVal = $('.loginPrefix,#snw_Enter_Login_Prefix').val();
        dataWraper = htmlVal.split('-')[0];
        $('.externalId,#snw_Enter_External_ID').val(dataWraper.toLowerCase() + '-' + loginUndifndChk.toLowerCase());
    };

    this.domainNameKeyUp = function($element, loginArray) {
        var inputDomVal = $element.val(),
            splitArry,
            t,
            loginUndifndChk = loginArray[1] != undefined ? loginArray[1] : '',
            dataWraper = '';
        if ($element.val().length > 120) {
            inputDomVal = inputDomVal.substr(0, 119);
            $element.val(inputDomVal);
        }
        if ($.trim(inputDomVal.length) != 0) {
            splitArry = inputDomVal.split(" ");
            for (t = 0; t < splitArry.length; t++) {
                dataWraper += splitArry[t].charAt(0);
            }
            $('.loginPrefix,.externalId,#snw_Enter_Login_Prefix,#snw_Enter_External_ID').val(dataWraper.toLowerCase() + '-' + loginUndifndChk.toLowerCase());
        } else {
            $('.loginPrefix,.externalId,#snw_Enter_Login_Prefix,#snw_Enter_External_ID').val(dataWraper.toLowerCase());
        }
    };

    this.popupBoxHandler = function() {
        var startDate,
            endDate;
        startDate = $('.subscription-start:visible').val();
        endDate = $('.subscription-end:visible').val();
        if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
            var dateSplitStrat = startDate.split('-');
            startDate = dateSplitStrat[2] + '-' + dateSplitStrat[0] + '-' + dateSplitStrat[1];
            var dateSplitend = endDate.split('-');
            endDate = dateSplitend[2] + '-' + dateSplitend[0] + '-' + dateSplitend[1];
        }
        if ((new Date(startDate).getTime() < new Date(endDate).getTime())) {
            $('.errormsg9_1').hide();
        } else {
            $('.errormsg9_1').show();
        }
    };

    this.submitDistrictHandler = function($element) {
        var lenght,
            trueCnt,
            startDate,
            endDate,
            dateSplitStrat,
            dateSplitend;
        lenght = $('.popupBox td>input[type="text"]').length;
        trueCnt = 0;
        $('.popupBox td>input[type="text"]').each(function() {
            if ($.trim($(this).val()) === '') {
                $(this).next('label').show();
            } else {
                $(this).next('label').hide();
                trueCnt++;
            }
        });
        if ($('#snwselectInput li>input:checkbox:checked').length === 0) {
            trueCnt--;
            $('.errormsg6').show();
        } else {
            if ($('#snwselectCourse li>input:checkbox:checked').length === 0) {
                trueCnt--;
                $('.errormsg6_1').show();
            }
        }
        startDate = $('.popupBox').find('.subscription-start').val();
        endDate = EndDate = $('.popupBox').find('.subscription-end').val();
        if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
            dateSplitStrat = startDate.split('-');
            startDate = dateSplitStrat[2] + '-' + dateSplitStrat[0] + '-' + dateSplitStrat[1];
            dateSplitend = endDate.split('-');
            endDate = dateSplitend[2] + '-' + dateSplitend[0] + '-' + dateSplitend[1];
        }
        if (lenght === trueCnt) {
            console.log(endDate);
            if ((new Date(startDate).getTime() < new Date(endDate).getTime())) {
                self.domainFormValidation(".popupBox", "create");
            } else {
                if (EndDate === "") {
                    self.domainFormValidation(".popupBox", "create");
                } else {
                    $('.hidee').hide();
                    $('.errormsg9_1').show();
                }
            }
        }
    };

    this.editSubmitHandler = function($element) {
        var lenght,
            trueCnt,
            startDate,
            endDate,
            dateSplitStrat,
            dateSplitend;
        lenght = $('.editpopupBox td>input[type="text"]').length;
        trueCnt = 0;
        $('.editpopupBox td>input[type="text"]').each(function() {
            if ($.trim($(this).val()) === '') {
                $(this).next('label').show();
            } else {
                trueCnt++;
            }
        });
        if ($('#snwSelectInput li>input:checkbox:checked').length === 0) {
            trueCnt--;
            $('.errormsg20').show();
        } else {
            if ($('#snwSelectCourse li').length != 1) {
                if ($('#snwSelectCourse li>input:checkbox:checked').length === 0) {
                    trueCnt--;
                    $('.errormsg21').show();
                }
            }
        }
        if (lenght === trueCnt) {
            startDate = $('.editpopupBox').find('.subscription-start').val();
            endDate = EndDate = $('.editpopupBox').find('.subscription-end').val();
            if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
                dateSplitStrat = startDate.split('-');
                startDate = dateSplitStrat[2] + '-' + dateSplitStrat[0] + '-' + dateSplitStrat[1];
                dateSplitend = endDate.split('-');
                endDate = dateSplitend[2] + '-' + dateSplitend[0] + '-' + dateSplitend[1];
            }
            console.log(endDate);
            if ((new Date(startDate).getTime() < new Date(endDate).getTime())) {
                self.domainFormValidation(".editpopupBox", "edit");
                self.cursorwait();
            } else {
                if (EndDate === "") {
                    self.domainFormValidation(".editpopupBox", "edit");
                    self.cursorwait();
                }
                $('.hidee').hide();
                $('.errormsg9_1').show();
            }
        }
    };

    this.schlEditSubmitHandler = function($element) {
        var localCount = 0,
            disabledCount = 0;
        $('.editschlpopupBox .formTable input[type="text"]').each(function() {
            if ($.trim($(this).val()) === '' && !$(this).is('[disabled=disabled]')) {
                $(this).next('label').show();

            } else if ($.trim($(this).val()) != '' && $(this).is('[disabled=disabled]')) {
                disabledCount++;
            } else {
                localCount++;
                $(this).next('label').hide();
            }
        });
        if (localCount === ($('.editschlpopupBox .formTable input[type="text"]').length - disabledCount)) {
            self.cursorwait();
            self.saveSchool($('.editschlpopupBox'), 'edit');
        }
    };

    this.editAddSchool = function($element) {
        var dId;
        $('.grayBg,.popGrayBg,.editschlpopupBox').hide();
        $('.popupBox,.editpopupBox,.addschlpopupBox').hide();
        dId = $element.parents('.editpopupBox').attr('data-domain-id');
        $('li#' + dId).find('li.addschool').trigger('click');
    };

    //      34894868
    this.schlsubmitHandler = function($element) {
        var localCount,
            disabledCount;
        $('.hidee').hide();
        localCount = 0;
        disabledCount = 0;
        $('.addschlpopupBox .formTable input[type="text"]').each(function() {
            if ($.trim($(this).val()) === '' && !$(this).is('[disabled=disabled]')) {
                $(this).next('label').show();

            } else if ($.trim($(this).val()) === '' && $(this).is('[disabled=disabled]')) {
                disabledCount++;
            } else {
                localCount++;
                $(this).next('label').hide();
            }
        });
        if (localCount === ($('.addschlpopupBox .formTable input[type="text"]').length - disabledCount)) {
            self.cursorwait();
            self.saveSchool($('.addschlpopupBox'), 'create');
        }
    };

    this.createPopupRadio = function($element) {
        var data_id = $element.attr('data-id');
        if (data_id === 'select') {
            $('#snwselectCourse li').each(function() {
                $(this).find('input').prop('checked', false);
            });
            $('#snwSelectCourse li').each(function() {
                $(this).find('input').prop('checked', false);
            });
        } else {
            $('#snwselectCourse li').each(function() {
                $(this).find('input').prop('checked', true);
            });
            $('#snwSelectCourse li').each(function() {
                $(this).find('input').prop('checked', true);
            });
        }
    };

    this.disTrictListHandler = function($element) {
        var html,
            result,
            attrId,
            thisContext;
        self.cursorwait();
        html = '';
        result = self.model.get('configResult');
        attrId = $element.data('id');
        thisContext = $element;
        self.model.set({
            '_districtContext': $element,
            '_globalId': attrId,
            '_globalThisContext': $element
        });
        //312
        self.model.ajaxObjCall({ //312
            type: "GET",
            url: result.getDomainData + '/' + attrId,
            crossDomain: true,
            contentType: "application/json"
        }, self.districtTabBoxData, self.commonError, void 0, {
            successVal: {
                thisContext: thisContext,
                attrId: attrId
            }
        });
    };

    this.showEntryHandler = function($element) {
        var result,
            html;
        self.model.set('_rowView', $element.val());
        self.cursorwait();
        result = self.model.get('configResult');
        html = '';
        self.model.ajaxObjCall({ //399
            type: "GET",
            url: result.DistrictList + '/' + self.model.get('_globalId') + '?querystring=' + self.model.get('_dataIdToshow') + '&limit=' + self.model.get('_rowView') + '',
            crossDomain: true,
            contentType: "application/json"
        }, self.displayEntry, self.commonError, void 0, {
            successVal: {
                html: html
            }
        })
    };

    this.NextCirHandler = function($element) {
        var result,
            html,
            attrId;
        result = self.model.get('configResult');
        if (self.model.get('_schoolRowDisableCheck') < self.model.get('_rowView')) {
            $('.NextCir').prop('disabled', true).addClass('disabled');
            return false;
        }
        $('.PreviousCir').removeAttr('disabled').removeClass('disabled');
        $('.pagination li').removeClass('disabled');
        attrId = $('.SAListTable tr:last').attr('data-id');
        self.model.set('_previousCount', self.model.get('_previousCount') + 1)
        if (self.model.get('_previousCount') === self.model.get('_previousCountArray').length) {
            self.model.set('_previousCountArray', self.model.get('_previousCountArray').push($('.SAListTable tr').eq(1).attr('data-id')));
        }
        self.cursorwait();
        html = '';
        self.model.ajaxObjCall({ //459
            type: "GET",
            url: result.schoolList + '/' + self.model.get('_globalId') + '?querystring=' + attrId + '&limit=' + self.model.get('_rowView') + '',
            crossDomain: true,
            contentType: "application/json",
        }, self.displayNextSchoolEntry, self.commonError, void 0, {
            successVal: {
                html: html
            }
        });
    };

    this.PreviousCirHandler = function($element) {
        var result,
            html;
        result = self.model.get('configResult');
        if (self.model.get('_previousCount') !== 0) {
            self.model.set('_previousCount', self.model.get('_previousCount') - 1);
        } else {
            $('.PreviousCir').prop('disabled', true).addClass('disabled');
            return false;
        }
        $('.NextCir').removeAttr('disabled').removeClass('disabled');
        self.cursorwait();
        html = '';
        self.model.ajaxObjCall({ //514
            type: "GET",
            url: result.schoolList + '/' + self.model.get('_globalId') + '?querystring=' + self.model.get('_previousCountArray')[self.model.get('_previousCount')] + '&limit=' + self.model.get('_rowView') + '',
            crossDomain: true,
            contentType: "application/json",
        }, self.displayNextSchoolEntry, self.commonError, void 0, {
            successVal: {
                html: html
            }
        })
    };

    this.DomainNextHandler = function($element) {
        var result,
            html,
            idUrl,
            idTrcaker,
            schlId;
        result = self.model.get('configResult');
        if (self.model.get('_nextdisbleChcek') < self.model.get('_dropdownCount')) {
            $('.DomainNext').prop('disabled', true).addClass('disabled');
            return false;
        }
        self.model.set('_landingPagePrevCount', self.model.get('_landingPagePrevCount') + 1);
        if (self.model.get('_landingPagePrevCount') === self.model.get('_previousLandingCountArray').length) {
            self.model.get('_previousLandingCountArray').push($('.domainContainer>ul>li:last-child').attr('id'));
        }
        $('.DomainPrevious').removeAttr('disabled').removeClass('disabled');
        idTrcaker = $('.domainContainer>ul>li:last-child').attr('id');
        self.cursorwait();
        html = '';
        idUrl = self.model.get('_postId');
        schlId = '';
        self.model.set('_baseUrl', result.DistrictList);
        self.model.ajaxObjCall({ //572
            type: "GET",
            url: self.model.get('_baseUrl') + '/' + idUrl + '?querystring=' + idTrcaker + '&limit=' + self.model.get('_dropdownCount') + '',
            crossDomain: true,
            contentType: "application/json"
        }, self.displayNextDomain, self.commonError, self.nextDomainCallComplete, {
            successVal: {
                html: html
            }
        });
    };

    this.DomainPreviousHandler = function($element) {
        var result,
            html,
            idUrl,
            schlId;
        result = self.model.get('configResult');
        if (self.model.get('_landingPagePrevCount') != 0) {
            self.model.set('_landingPagePrevCount', self.model.get('_landingPagePrevCount') - 1);
        } else {
            $('.DomainPrevious').prop('disabled', true).addClass('disabled');
            return false;
        }
        self.model.set('_landingPagePrevCount', self.model.get('_landingPagePrevCount') == -1 ? 0 : self.model.get('_landingPagePrevCount'));
        self.cursorwait();
        $('.DomainNext').removeAttr('disabled').removeClass('disabled');
        html = '';
        idUrl = self.model.get('_postId');
        schlId = '';
        self.model.set('_baseUrl', result.DistrictList);
        self.model.ajaxObjCall({ //630
            type: "GET",
            url: self.model.get('_baseUrl') + '/' + idUrl + '?querystring=' + self.model.get('_previousLandingCountArray')[self.model.get('_landingPagePrevCount')] + '&limit=' + self.model.get('_dropdownCount') + '',
            crossDomain: true,
            contentType: "application/json"
        }, self.displayNextDomain, self.commonError, self.nextDomainCallComplete, {
            successVal: {
                html: html
            }
        });
    };

    this.domainShowEntryHandler = function($element) {
        var result,
            html,
            idUrl,
            schlId;
        result = self.model.get('configResult');
        self.model.set('_dropdownCount', $element.val());
        self.cursorwait();
        html = '';
        idUrl = self.model.get('_postId');
        schlId = '';

        self.model.set('_baseUrl', result.DistrictList);
        self.model.ajaxObjCall({ //685
            type: "GET",
            url: self.model.get('_baseUrl') + '/' + idUrl + '?querystring=0&limit=' + self.model.get('_dropdownCount') + '',
            crossDomain: true,
            contentType: "application/json"
        }, self.domainShowEntry, self.commonError, self.nextDomainCallComplete, {
            successVal: {
                html: html
            }
        });
    };

    this.newDomTabHandler = function($element) {
        $('.newDomTab li').each(function() {
            if ($(this).hasClass('active')) {
                $(this).removeClass('active');
            }
        });
        $element.addClass('active');
        $('.newDomTab li').each(function(g) {
            if ($(this).hasClass('active')) {
                $('#panel_' + g + '').show();
            } else {
                $('#panel_' + g + '').hide();
            }
        });
    };

    this.adminNav = function($element) {
        if ($element.hasClass('active')) {
            $('.adminNavList ul').slideUp(500);
            $('.adminNav li').removeClass('active');
        } else {
            $('.adminNav li').removeClass('active');
            $element.addClass('active');
            var localIndex = $element.index();
            $('.adminNavList ul').slideUp(500);
            var cssLeft = $element.position().left;
            var cssTop = $element.position().top;
            $('.adminNavList ul:eq(' + localIndex + ')').css({
                'left': cssLeft,
                'top': cssTop
            }).slideDown(500);
        }
    };
    this.adminNavFirst = function($element) {
        var localIndex, cssLeft, cssTop;
        if ($element.hasClass('active')) {
            $('.adminNavList ul').slideUp(500);
            $('.adminNav li').removeClass('active');
        } else {
            $('.adminNav li').removeClass('active');
            $element.addClass('active');
            localIndex = $element.index();
            $('.adminNavList ul').slideDown(500);
            cssLeft = $element.position().left;
            cssTop = $element.position().top;
            $('.adminNavList ul').css({
                'left': cssLeft,
                'top': cssTop
            }).slideDown(500);
        }
    };
    this.adminNavZero = function() {
        $('.adminNavList ul').slideUp(500);
        $('.adminNav li').removeClass('active');
        $('#snw-license-usage-report02,#snw-license-usage-report05,.user').hide();
        $('#adminContainer,.welcome').show();
    }
    this.courseCatalogHandler = function(e) {
        e.stopPropagation();
        if ($('.corsecatalogList').is(':visible')) {
            $('.corsecatalogList').hide();
        } else {
            $('.corsecatalogList').show();
            $('.corseselectList').hide();
        }
        if ($('.corsecatalog .editableCatalog .catalogList').length == 0) {
            $('.corsecatalog .editableCatalog').html('<label class="slectdemo">Select</label>');
        }
        return false;
    };

    this.courseSelectHandler = function(e) {
        e.stopPropagation();
        if ($('.editpopupBox').is(':visible')) {
            self.editnewfunction();
        }
        if ($('.corseselectList').is(':visible')) {
            $('.corsecatalogList').hide();
        } else {
            $('.corseselectList').show();
            $('.corsecatalogList').hide();
        }
        return false;
    };

    this.SearchClickHandler = function() {
        var result,
            srchData,
            html;
        result = self.model.get('configResult');
        srchData = $('.searchbox').val();
        if ($.trim(srchData) === "") {
            alert("Please enter search value");
            return false;
        }
        self.cursorwait();
        html = '';
        self.model.ajaxObjCall({ //809
            type: "GET",
            url: result.DistrictList + '/' + self.model.get('_postId') + '?querystring=0&searchtext=' + srchData + '&limit=' + self.model.get('_rowView') + '',
            crossDomain: true,
            contentType: "application/json"
        }, self.searchDataDisplay, self.commonError, void 0, {
            successVal: {
                html: html
            }
        });
    };

    this.documentClickHandler = function(e) {
        if ('loginPerson' != $(e.target).attr('class')) {
            if ('loggerName' != $(e.target).attr('class')) {
                $('#snwLPMenu').hide();
            } else {
                $('#snwLPMenu').show();
            }
        }
        if ('loggerName' != $(e.target).attr('class')) {
            if ('loginPerson' != $(e.target).attr('class')) {
                $('#snwLPMenu').hide();
            } else {
                $('#snwLPMenu').show();
            }
        }
        if ($('.corseselectList').has(e.target).length === 0) {
            $('.corseselectList').hide();
        }
        if ($('.corsecatalogList').has(e.target).length === 0) {
            $('.corsecatalogList').hide();
        }
        if ($('.stateDataListing').has(e.target).length === 0) {
            $('.stateDataListing').hide();
        }
        if ($('.domainDataListing').has(e.target).length === 0) {
            $('.domainDataListing').hide();
        }
        if ($('.corsecatalog .editableCatalog .catalogList').length == 0) {
            $('.corsecatalog .editableCatalog').html('<label class="slectdemo">Select</label>');
        }
    };

    this.distrctAddHandler = function($element) {
        $('.popupBox').find('.entire-catalog').prop("checked", true);
        $('.popupBox').find('.inputTracker input').prop("checked", false);
        $('.hidee').hide();
        $('.createdomainlist').html($('.statename').html());
        $('[data-toggle="tooltip"]').tooltip();
        $('.popupBox').fadeIn('slow');
        $('.grayBg,.popGrayBg').css({
            'height': $('body').height()
        });
        $('.popupBox .formTable input').each(function() {
            $(this).val('');
        });
        $('.createdomainlist').val(self.model.get('_optionselector'));
        self.getProviderList();
        $('#snwsubscription-start,#snwpilotenddate').val(self.getCurrentDate());
    };


    this.catalogListInputHandler = function($element) {
        var inputVal,
            thisId,
            domainId,
            lcalText;
        inputVal = $element.parent().text();
        thisId = $element.attr('id');
        domainId = $element.attr('data-domain-id');
        if ($element.is(":checked")) {
            if ($('.corsecatalog .slectdemo').length > 0) {
                $('.corsecatalog .editableCatalog').html('<label id="' + thisId + '" class="catalogList">' + inputVal + '</label>');
            } else {
                $('.corsecatalog .editableCatalog').append('<label id="' + thisId + '" class="catalogList">' + ',' + ' ' + inputVal + '</label>');
            }
            $('#demo' + domainId).attr('checked', 'checked');
        } else {
            $('#demo' + domainId).removeAttr('checked');
            $('.editableCatalog #' + thisId).remove();
        }
        lcalText = $('.corsecatalog .editableCatalog .catalogList').eq(0).text();
        $('.corsecatalog .editableCatalog .catalogList').eq(0).text(lcalText.replace(/,/g, ""));
    };


    this.editHandler = function($element) {
        $('.editpopupBox').find('.inputTrackerEdit input').prop("checked", false);
        $('.editdomainlist').html($('.statename').html());
        $('.editpopupBox').fadeIn('slow');
        $('.grayBg,.popGrayBg').css({
            'height': $('body').height()
        });
        $('.hidee').hide();
        $('.formTable .domain-name').val($element.parents('.districtTabBox').find('.disTrictList').text());
        $('.formTable .domain-name').attr('value', $element.parents('.districtTabBox').find('.disTrictList').text());
        $('.editpopupBox').find('.editwelcome').data("domain-id", $element.closest(".districtTabBox").find("h1.disTrictList").data("id"));
        $('.editpopupBox').find('.editsubmit').data("domain-id", $element.closest(".districtTabBox").find("h1.disTrictList").data("id"));
        $('.editpopupBox').attr("data-domain-id", $element.closest(".districtTabBox").find("h1.disTrictList").data("id"));
        self.cursorwait();
        self.getProviderList();
        self.model.set('_editbutton', $element);
        $('.editdomainlist').val(self.model.get('_optionselector'));
        $('.editdomainlist').attr('value', self.model.get('_optionselector'));
    };

    this.addSchoolHandler = function($element) {
        $('.hidee').hide();
        var attrId = $element.parents('.districtTabBoxHead').find('.disTrictList').data('id');
        $('#snw_Enter_Login_Prefix,#snw_Enter_External_ID,#snw_Enter_School').val('');
        self.cursorwait();
        $('.addschoollist').html($('.statename').html());
        $('.addschlpopupBox,.grayBg').show();
        $('.grayBg,.popGrayBg').css({
            'height': $('body').height()
        });
        $('.addschoollist').val(self.model.get('_optionselector'));
        $('.schooldistrictlist').val(attrId);
        $('#snw_popupInputDate1').val(self.getCurrentDate());
        self.addSchool(self.model.get('_optionselector'), attrId);
    };

    this.addSchlPopupBox = function($element) {
        self.cursorwait();
        var dataVal = $element.val();
        self.addSchool(dataVal, 0);
    };

    this.editSchoolDistrictList = function($element) {
        self.cursorwait();
        var dataVal = $element.val();
        self.schoolPopupData(dataVal);
    };

    this.courseCatalogListInput = function($element) {
        var localLength,
            lclLngthEdit,
            indexArray, data_domain_id,
            indexArrayEdit,
            i;
        data_domain_id = $element.attr('data-domain-id');
        if ($element.is(':checked')) {
            $('#example' + data_domain_id + ':visible').prop("checked", true);
            $('#demo' + data_domain_id + ':visible').prop("checked", true);
            if ($('input[data-domain-id="34816208"]').is(':checked')) {
                $('.inputTracker input').prop("checked", false);
                $('.inputTrackerEdit input').prop("checked", false);
            }
            if ($element.attr('data-domain-id') == "34816208") {
                $('.inputTracker input').prop("checked", false);
                $('.inputTrackerEdit input').prop("checked", false);
            }
            $('#demo' + data_domain_id).attr('checked', 'checked');
        } else {
            $('#example' + data_domain_id + ':visible').prop("checked", false);
            $('#demo' + data_domain_id + ':visible').prop("checked", false);
            if ($element.attr('data-domain-id') == "34816208") {
                localLength = $('#snwselectInput Input:checkbox:checked').length;
                lclLngthEdit = $('#snwSelectInput Input:checkbox:checked').length;
                indexArray = [];
                indexArrayEdit = [];
                for (i = 0; i < localLength; i++) {
                    indexArray.push($('#snwselectInput Input:checkbox:checked:eq(' + i + ')').attr('data-domain-id'));
                    $('#demo' + indexArray[i]).prop("checked", true);
                }
                for (i = 0; i < lclLngthEdit; i++) {
                    $('#snwCorsecatalogList').hide();
                    indexArrayEdit.push($('#snwSelectInput Input:checkbox:checked:eq(' + i + ')').attr('data-domain-id'));
                    $('#example' + indexArrayEdit[i]).prop("checked", true);
                }
            }
            $('#demo' + data_domain_id).removeAttr('checked');
        }
        self.updateCourseList();
    };

    this.editSchoolPopHandler = function($element) {
        var stateSelctedVal = $('.statename').val();
        $('.hidee').hide();
        $('.addschoollist').html($('.statename').html());
        self.cursorwait();
        self.model.set('_domainId', $element.parents('tr').attr('data-id'));
        $('.addschoollist').val(stateSelctedVal);
        self.editSchool($('.editschlpopupBox'), $element.parents('tr').attr('data-id'), stateSelctedVal);
    };

    this.beforeSendEventHandler = function(xhr, settings) {
        xhr.setRequestHeader('Authorization', 'Bearer ' + self.model.get('_globalSccessToken'));
    };


    //reports start

    this.snwReportsLienceLiFirst = function($element) {
        var html, i;
        $('.total-value-col,.user').hide();
        $('#snw-val051').prop('checked', true);
        $('.domainInput').html('<li class="massge">Please select a state first.</li>');
        $('#snw-license-usage-report02,.License').show();
        $('#snw-license-usage-report05').hide();
        $('#adminContainer').hide();
        $('.adminNavList ul').slideUp(500);
        $('#snwSubscription-end2').val('');
        $('#snwSubscription-start2').val(self.getCurrentDate());
        html = '';
        $('.stateInput').html(function() {
            for (i = 0; i < self.model.get('_stateId').length; i++) {
                html += '<li><input id="checkbox' + i + '" data-test="stateTest' + i + '" type="checkBox" data-value="' + self.model.get('_stateId')[i] + '">' + self.model.get('_stateName')[i] + '</li>';

            }
            return html;
        });
        $('.stateDataListing Input[type="checkbox"]').off('click').on('click', function() {
            self.stateDataListingInput($(this));
        });
    };

    this.stateDataListingInput = function($element) {
        var html = '',
            i,
            stateSelctedVal = $element.attr('data-value'),
            stateTest = $element.attr('data-test');
        if ($element.is(':checked')) {
            self.model.ajaxObjCall({ //repo685
                type: "GET",
                url: self.model.get('_config').DistrictList + '/' + stateSelctedVal
            }, self.stateDataListingInputSucc, self.commonError, void 0, {
                successVal: {
                    stateTest: stateTest,
                    html: html
                }
            });
        } else {
            $('.domainInput input[data-test~=' + stateTest + ']').parent().remove();
        }
        if ($('.stateDataListing Input').is(':checked')) {
            $('.domainInput .massge').remove();
        } else {
            $('.domainInput').html('<li class="massge">Please select a state first.</li>');
        }
    };

    //repo685
    this.stateDataListingInputSucc = function(rslte, parametersObj) {
        var i,
            stateTest = stateTest,
            html = html;
        if (rslte.messageType === "SUCCESS") {
            $('.domainInput').append(function() {
                for (i = 0; i < rslte.data.domains.length; i++) {
                    html += '<li><input id="checkbox' + i + '" data-test="' + stateTest + '" type="checkBox" data-value="' + rslte.data.domains[i].id + '">' + rslte.data.domains[i].name + '</li>';

                }
                return html;
            });
            self.cursordefault();
        }
        self.cursordefault();
    };


    this.snwreportsLienceLiSec = function($element) {
        var html,
            i;
        $('.total-value-col,.user').hide();
        $('#snwSubscription-end5').val('');
        $('#snwSubscription-start5').val(self.getCurrentDate());
        $('#snw-license-usage-report02').hide();
        $('#snw-license-usage-report05,.Pilot').show();
        $('#adminContainer').hide();
        $('.adminNavList ul').slideUp(500);
        html = '';
        $('.stateInput').html(function() {
            for (i = 0; i < self.model.get('_stateId').length; i++) {
                html += '<li><input id="checkbox' + i + '" type="checkBox" data-value="' + self.model.get('_stateId')[i] + '">' + self.model.get('_stateName')[i] + '</li>';

            }
            return html;
        });
        $('.stateInput input[type="checkbox"]').off('click').on('click', function() {
            self.model.set('_stateReportId', []);
            $('.stateInput').find('Input:checkbox:checked').each(function() {
                self.model.get('_stateReportId').push($(this).attr('data-value'));
            });
            console.log(self.model.get('_stateReportId'));
        });
        $('#snwsubscription-start5').val(self.getCurrentDate());
    };


    this.reportfisrteditHandler = function() {
        $('iframe').contents().find(".findSolution").trigger('click');
        self.cursorwait();
        var timer = 500;
        if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
            timer = 500;
        }
        setTimeout(function() {
            self.reportFisrtEditTimeOut();
        }, timer);
    }



    this.reportFisrtEditTimeOut = function() {
        var startDate,
            endDate,
            checkUrl;
        self.model.set('_ditrictReportId', []);
        $('.domainInput input').each(function() {
            if ($(this).is(':checked')) {
                self.model.get('_ditrictReportId').push($(this).attr('data-value'));
            }
        });
        $('.hidee').hide();
        startDate = $('#snwSubscription-start2').val().replace(/\-/g, '/');
        endDate = $('#snwSubscription-end2').val().replace(/\-/g, '/');
        if ($('.stateInput input').is(':checked')) {

            $('.exL1').hide();
            if ($('.domainInput input').is(':checked')) {
                $('.exL2').hide();
            } else {
                $('.exL2').show();
            }
        } else {
            $('.exL1').show();
        }
        if (startDate == "" && endDate == "") {
            $('.bothL').show();
        }
        if ((new Date(startDate).getTime() > new Date(self.getCurrentDate()).getTime())) {
            $('.st1').show();
        }
        if ((new Date(endDate).getTime() < new Date(startDate).getTime())) {
            $('.end1').show();
        }
        checkUrl = "districtlicensereport";
        if ($('#snw-val051').is(':checked')) {
            checkUrl = "reports/districtlicensereport";
        }
        if ($('#snw-val061').is(':checked')) {
            checkUrl = "reports/courselicensereport";
        }
        self.firefoxTimeOut(startDate, endDate, checkUrl);
        $('#snw-license-usage-report02').find('iframe').on('load', function() {
            self.snwLicenseUsageReportFunc($(this));
        });
    };


    this.firefoxTimeOut = function(startDate, endDate, checkUrl) {
        var startdateCopy,
            endDateCopy,
            $baseURL,
            dateSplitStrat,
            dataUrl,
            dateSplitend,
            $baseURL1,
            dataUrl1,
            cuurentDate,
            cuurDt;
        if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
            startdateCopy = startDate,
                endDateCopy = endDate;
            dateSplitStrat = startDate.split('/');
            startDate = dateSplitStrat[2] + '-' + dateSplitStrat[0] + '-' + dateSplitStrat[1];
            dateSplitend = endDate.split('/');
            endDate = dateSplitend[2] + '-' + dateSplitend[0] + '-' + dateSplitend[1];
            cuurentDate = self.getCurrentDate().split('-');
            cuurDt = cuurentDate[2] + '-' + cuurentDate[0] + '-' + cuurentDate[1];
            console.log(cuurDt);
            if ((new Date(startDate).getTime() <= new Date(cuurDt).getTime()) && (new Date(endDate).getTime() > new Date(startDate).getTime()) && $('.stateInput input').is(':checked') && $('.domainInput input').is(':checked')) {

                $baseURL = window.location.protocol + '//' + window.location.hostname + (window.location.port && (':' + window.location.port)) + '/';
                dataUrl = $baseURL + checkUrl + '?startdate=' + startdateCopy + '&enddate=' + endDateCopy + '&domainid=' + self.model.get('_ditrictReportId').join();

                $('#snw-license-usage-report02').find('.value-row').html('<iframe src=""></iframe>');
                $('#snw-value1').show();
                console.log(dataUrl);
                $('#snw-license-usage-report02').find('iframe').attr('src', dataUrl);

            }
        } else {
            if ((new Date(startDate).getTime() <= new Date(self.getCurrentDate()).getTime()) && (new Date(endDate).getTime() > new Date(startDate).getTime()) && $('.stateInput input').is(':checked') && $('.domainInput input').is(':checked')) {

                $baseURL1 = window.location.protocol + '//' + window.location.hostname + (window.location.port && (':' + window.location.port)) + '/';
                dataUrl1 = $baseURL1 + checkUrl + '?startdate=' + startDate + '&enddate=' + endDate + '&domainid=' + self.model.get('_ditrictReportId').join();

                $('#snw-license-usage-report02').find('.value-row').html('<iframe src=""></iframe>');
                $('#snw-value1').show();
                $('#snw-license-usage-report02').find('iframe').attr('src', dataUrl1);

            }
        }
        console.log(startDate, endDate);
    };


    this.snwLicenseUsageReportFunc = function($element) {
        var dataElement = $element,
            myVar = setInterval(function() {
                if (!dataElement.contents().find('#container').is(':empty')) {
                    self.cursordefault();
                    $('#snw-license-usage-report02').find('iframe').contents().find('.jrPage').css('width', '100%');
                    clearInterval(myVar);
                }
            }, 600);
        console.log('laod the iframe');
    };


    this.reportPostSec = function() {
        $('iframe').contents().find(".findSolution").trigger('click');
        self.cursorwait();
        var timer = 500;
        if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
            timer = 500;
        }
        setTimeout(function() {
            self.reportPost2TimeOut()
        }, timer);
    }


    this.reportPost2TimeOut = function() {

        $('.hidee').hide();
        var startDate = $('#snwSubscription-start5').val().replace(/\-/g, '/'),
            endDate = $('#snwSubscription-end5').val().replace(/\-/g, '/');
        if ($('.stateInput input').is(':checked')) {
            $('.ex1').hide();
        } else {
            $('.ex1').show();
        }
        if (startDate == "" && endDate == "") {
            $('.both').show();
        }
        if ((new Date(startDate).getTime() > new Date(self.getCurrentDate()).getTime())) {
            $('.ex2').show();
        }
        if ((new Date(endDate).getTime() < new Date(startDate).getTime())) {
            $('.ex3').show();
        }
        self.reportpost2Firefox(startDate, endDate);
        $('#snw-license-usage-report05').find('iframe').on('load', function() {
            self.snwLicenseUsageReportFifth($(this));
        });

    };


    this.reportpost2Firefox = function(startDate, endDate) {
        var startdateCopy,
            endDateCopy,
            dateSplitStrat,
            $baseURL,
            dataUrl,
            $baseURL1,
            dataUrl1,
            dateSplitend,
            cuurentDate, cuurDt;
        console.log(startDate, endDate);
        if (navigator.userAgent.toLowerCase().indexOf('firefox') > -1) {
            startdateCopy = startDate;
            endDateCopy = endDate;
            dateSplitStrat = startDate.split('/');
            startDate = dateSplitStrat[2] + '-' + dateSplitStrat[0] + '-' + dateSplitStrat[1];
            dateSplitend = endDate.split('/');
            endDate = dateSplitend[2] + '-' + dateSplitend[0] + '-' + dateSplitend[1];
            cuurentDate = self.getCurrentDate().split('-');
            cuurDt = cuurentDate[2] + '-' + cuurentDate[0] + '-' + cuurentDate[1];
            console.log(cuurDt);
            if ((new Date(startDate).getTime() <= new Date(cuurDt).getTime()) && (new Date(endDate).getTime() > new Date(startDate).getTime()) && $('.stateInput input').is(':checked')) {

                $baseURL = window.location.protocol + '//' + window.location.hostname + (window.location.port && (':' + window.location.port)) + '/';
                dataUrl = $baseURL + self.model.get('_config').pilot_report + '?startdate=' + startdateCopy + '&enddate=' + endDateCopy + '&domainid=' + self.model.get('_stateReportId').join();

                $('#snw-license-usage-report05').find('.value-row').html('<iframe src=""></iframe>');
                $('#snw-value3').show();
                console.log(dataUrl)
                $('#snw-license-usage-report05').find('iframe').attr('src', dataUrl);
            }
        } else {
            if ((new Date(startDate).getTime() <= new Date(self.getCurrentDate()).getTime()) && (new Date(endDate).getTime() > new Date(startDate).getTime()) && $('.stateInput input').is(':checked')) {
                console.log(new Date(startDate).getTime(), new Date(self.getCurrentDate()).getTime(), new Date(endDate).getTime());
                $baseURL1 = window.location.protocol + '//' + window.location.hostname + (window.location.port && (':' + window.location.port)) + '/';
                dataUrl1 = $baseURL1 + self.model.get('_config').pilot_report + '?startdate=' + startDate + '&enddate=' + endDate + '&domainid=' + self.model.get('_stateReportId').join();

                $('#snw-license-usage-report05').find('.value-row').html('<iframe src=""></iframe>');
                $('#snw-value3').show();
                console.log(dataUrl)
                $('#snw-license-usage-report05').find('iframe').attr('src', dataUrl1);
            }
        }
        console.log(startDate, endDate);
    };

    this.snwLicenseUsageReportFifth = function($element) {
        var dataElement = $element,
            // console.log($element);
            myVarr = setInterval(function() {
                // console.log($dataElement);
                if (!dataElement.contents().find('#container').is(':empty')) {
                    // console.log($element);
                    self.cursordefault();
                    $('#snw-license-usage-report05').find('iframe').contents().find('.jrPage').css('width', '100%');
                    clearInterval(myVarr);
                }
            }, 800);
        // self.cursordefault();
        console.log('laod the iframe');
    };
    this.stateListingHandler = function(e) {
        e.stopPropagation();
        if ($('.stateDataListing').is(':visible')) {
            $('.stateDataListing').hide();
        } else {
            $('.stateDataListing').show();
        }
        if ($('.domainDataListing').is(':visible')) {
            $('.domainDataListing').hide();
        }
        return false;
    };
    this.domainListingHandler = function(e) {
        e.stopPropagation();
        if ($('.domainDataListing').is(':visible')) {
            $('.domainDataListing').hide();
        } else {
            $('.domainDataListing').show();
        }
        return false;
    };
};