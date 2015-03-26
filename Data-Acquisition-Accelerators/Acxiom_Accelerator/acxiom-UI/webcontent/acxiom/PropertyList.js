/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
define(["dojo/_base/declare", "dijit/_WidgetBase", "dijit/_TemplatedMixin", "dijit/_WidgetsInTemplateMixin",
        "dojo/keys", "dijit/registry", "dojo/request/xhr", "dojo/store/Memory", "dojo/fx/Toggler", "dojo/_base/array", "dojo/text!./templates/PropertyList.html", "./PropertyListRow",
        "dijit/form/Button", "dijit/form/FilteringSelect", "dijit/form/TextBox"],

    function(declare, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin,
            keys, registry, xhr, Memory, Toggler, arrayUtil, template, PropertyListRow) {
        return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
            templateString: template,
            
            _showHidePropTitle: null,
            _searchTermStore: null,
            childRows: null,
            readynessForSubmit: false,
            dppaSearchFilterProperty: null,
            glbaSearchFilterProperty: null,
            
            constructor: function() {
                this.childRows = []; 
            },
            
            postCreate: function() {
                this.inherited(arguments);
               
                this._showHidePropTitle = new Toggler({
                    node: this.searchTermPropTitle
                }); 
                this.own(this._showHidePropTitle);
                
                this._loadData(); //set the Search term properties

                var evaluateReadyness =  this._evaluateReadynessForSubmit.bind(this);
                
                //dppa - ensure a valid entry
                this.dppaPropertyType.watch("state", evaluateReadyness);
                this.dppaPropertyType.watch("displayedValue", evaluateReadyness);
                
                //glba - ensure a valid entry
                this.glbaPropertyType.watch("state", evaluateReadyness);
                this.glbaPropertyType.watch("displayedValue", evaluateReadyness);

                this.lstPropertyType.set("value", "");

                var reEvaluate = this._reEvaluateEnabledness.bind(this);                
                this.lstPropertyType.watch("state", reEvaluate);
                this.lstPropertyType.watch("displayedValue", reEvaluate);
                                
                this._reEvaluateEnabledness();
            },
            
            destroy: function() {
                this.clearRows();
                this.inherited(arguments);
            },

            _loadData: function() {
                var self = this;
                xhr("./acxiom/config/acxiomsearch.xml", {
                    handleAs: "xml"
                }).then(function(data){
                    
                    // Process Acxiom query data
                    var properties = _XML2json(data.documentElement)                        
                    self._setSearchFilters(properties);
                    self._setSearchTermStore(properties);

                    }, function(err){
                    // Handle the error condition
                        alert("Error: cannot load Acxiom data. Error is: " + err);
                    });
            },                    

            _reEvaluateEnabledness: function() {
                var isEnabled = true; //assume everything is good
                
                // state is empty when the list is valid
                if (this.lstPropertyType.get("state") ||
                    !this.lstPropertyType.get("displayedValue")) {
                    isEnabled = false;
                } 
   
                this.btnInsert.set("disabled", !isEnabled);
            },


            _setSearchOptions: function() {
                    
                //set items to property store
                var propStore = this.lstPropertyType.get('store');
                this._searchTermStore.query({QueryType: /.*/}).forEach(function(propertyQuery){
                    // called for each match
                    propStore.put({ id: propertyQuery.QueryType, label: "enter in....", name: propertyQuery.QueryType,  value: propertyQuery.PropertyList  });

                });

            },

            //set the GLBA and DPPA search filter selections.
            _setSearchFilters: function(searchFilters) {
                    
                //set DPPA & GLBA values into store for UI select list
                
                //set dppa properties
                this.set("dppaSearchFilterProperty", searchFilters.DppaSearchFilter.SearchFilter);
                var dppaList = searchFilters.DppaSearchFilter.SelectionList.split(",");
                var dppaStore = this.dppaPropertyType.get('store');
                dppaList.forEach(function(dppaItem) {
                    dppaItem = dppaItem.replace(/^\s+|\s+$/gm,''); //remove leading and trailing blanks
                    dppaStore.put({id: "DPPA" + dppaItem, name:dppaItem, value:dppaItem}); // store the object with the given identity
                });
                this.dppaPropertyType.set("value", dppaStore.getIdentity(dppaStore.data[0]));//set the default value, index 0, in selection list
                 
                //set glba properties
                this.set("glbaSearchFilterProperty", searchFilters.GlbaSearchFilter.SearchFilter);
                var glbaList = searchFilters.GlbaSearchFilter.SelectionList.split(",");
                var glbaStore = this.glbaPropertyType.get('store');
                glbaList.forEach(function(glbaItem) {
                    glbaItem = glbaItem.replace(/^\s+|\s+$/gm,''); //remove leading and trailing blanks
                    glbaStore.put({id: "GLBA" + glbaItem, name:glbaItem, value:glbaItem}); // store the object with the given identity
                });
                this.glbaPropertyType.set("value", glbaStore.getIdentity(glbaStore.data[0]));//set the default value, index 0, in selection list

            },

            _setSearchTermStore: function(searchNode) {          
                this._searchTermStore = new Memory({idProperty: "QueryType", data: searchNode.SearchTerm});
                this._setSearchOptions();// load the property store
            },
                        
            displayQueryRows: function() {

                if ( this.childRows.length != 0) { //clear rows if currently being displayed
                    this.clearRows();
                }
                var propertyTypeName = this.lstPropertyType.get("value");
 
                // Show the node
                this._showHidePropTitle.show();

                this.qryViewSelected.textContent = propertyTypeName;

                var qryresult = this._searchTermStore.query({QueryType:propertyTypeName })[0]  //Returns the array query object
                var propList = qryresult.PropertyList.split(',');
                var self = this;
                arrayUtil.forEach(propList, function(property, i){
                    self.insertRow(property);
                });

                this.lstPropertyType.set("value", "");
                this.lstPropertyType.focus();
                
            },
                        
            insertRow: function(propertyName) {

                var newRow = new PropertyListRow({
                    propertyTypeName: propertyName,
                    propertyValue: ""
                });
                this.childRows.push(newRow);
                
                newRow.txtValue.set("placeHolder", "Enter " + propertyName); 
                 
                newRow.on("removeRequested", this._removeRequested.bind(this));
                newRow.watch("propertyValue", this._evaluateReadynessForSubmit.bind(this));
                
                
                // N.B. placeAt automatically calls .startup() on the widget if not already started
                newRow.placeAt(this.contentsNode, "last");              
                
             },
            
            clearRows: function() {
                this.childRows.forEach(function(row) {
                    row.destroyRecursive();
                });
                
                this.childRows = [];
                this.qryViewSelected.textContent = "";
                this.set("readynessForSubmit",false);
                // Hide the node
                this._showHidePropTitle.hide();

             },

            _evaluateReadynessForSubmit: function() {
                var isEnabled = true;

                if (this.childRows.length < 1 ){
                        isEnabled = false;          
                } else 
                    if (this.dppaPropertyType.get("state") ||
                        !this.dppaPropertyType.get("displayedValue")) {
                        isEnabled = false;
                     } else
                        if (this.glbaPropertyType.get("state") ||
                            !this.glbaPropertyType.get("displayedValue")) {
                            isEnabled = false;
                        } else {
                 
                            // make sure all the properties have values               
                            this.childRows.forEach(function(propRow) {
                                if (!propRow.get("propertyValue")) { //non-blank
                                    isEnabled = false;
                                    return;//like a break
                                }

                            });
                        }                
                this.set("readynessForSubmit",isEnabled);
            },
                       
            _removeRequested: function(row) {
                // Remove the child from our list.
                var index = this.childRows.indexOf(row);
                this.childRows.splice(index, 1);
                
                row.destroyRecursive();
            }            
        });
    });

    function _XML2json(node) {

	    var	data = {};

	    // append a value
	    function Add(name, value) {
		    if (data[name]) {
			    if (data[name].constructor != Array) {
				    data[name] = [data[name]];
			    }
			    data[name][data[name].length] = value;
		    }
		    else {
			    data[name] = value;
		    }
	    };
	
	    // element attributes
	    var c, cn;
	    for (c = 0; cn = node.attributes[c]; c++) {
		    Add(cn.name, cn.value);
	    }
	
	    // child elements
	    for (c = 0; cn = node.childNodes[c]; c++) {
		    if (cn.nodeType == 1) {
			    if (cn.childNodes.length == 1 && cn.firstChild.nodeType == 3) {
				    // text value
				    Add(cn.nodeName, cn.firstChild.nodeValue);
			    }
			    else {
				    // sub-object
				    Add(cn.nodeName, _XML2json(cn));
			    }
		    }
	    }

	    return data;

    };
