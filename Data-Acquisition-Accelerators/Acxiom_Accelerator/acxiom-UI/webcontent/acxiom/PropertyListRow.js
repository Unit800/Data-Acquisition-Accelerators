/*******************************************************************************
 * Licensed Materials - Property of IBM
 * (C) Copyright IBM Corp. 2015 All Rights Reserved
 * US Government Users Restricted Rights - Use, duplication or
 * disclosure restricted by GSA ADP Schedule Contract with IBM Corp.
 *******************************************************************************/
/*globals define: true */
define(["dojo/_base/declare", "dojo/_base/lang", "dijit/_WidgetBase",
        "dijit/_TemplatedMixin", "dijit/_WidgetsInTemplateMixin",
        "dojo/text!./templates/PropertyListRow.html"],
    function (declare, dojo, _WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin,
            template) {
        return declare([_WidgetBase, _TemplatedMixin, _WidgetsInTemplateMixin], {
            templateString: template,
            
            propertyTypeName: "",
            propertyValue: "",
            
            postCreate: function() {
                this.inherited(arguments);
                
                this.txtValue.watch("displayedValue", this._displayedValueChanged.bind(this));
            },
             
            _setPropertyTypeNameAttr: {
                node: "txtPropertyTypeName",
                type: "innerText"
            },
            
            _setPropertyValueAttr: {
                node: 'txtValue',
                type: 'attribute',
                attribute: 'displayedValue'
            },
            
            _btnRemoveClicked: function() {
                this.onRemoveRequested(this);
            },
            
            _onPropValueBlur: function() {
                 var propValue = this.get("propertyValue").replace(/^\s+|\s+$/gm,'');//remove leading and trailing blanks
                 if ( !propValue ){//all blanks are not allowed
                     this.set("propertyValue", propValue);
                 }
            },            
            _displayedValueChanged: function(name, oVal, nVal) {
                this.set("propertyValue", nVal);
            },
            
            // Placeholder for removeRequested event.
            onRemoveRequested: function(row) {
            }
        });
    });
