jQuery.sap.declare("pmc.util.MyTimeline");
jQuery.sap.require("sap.suite.ui.commons.Timeline");
jQuery.sap.require("sap.suite.ui.commons.TimelineRenderer");

var MyTimeline = sap.suite.ui.commons.Timeline.extend(
		"pmc.util.MyTimeline", {
			renderer : "sap.suite.ui.commons.TimelineRenderer",
			metadata : {
				properties : {
					"showFilterBar": {
		                type: "boolean",
		                group: "Misc",
		                defaultValue: false
		            }
				}
			}
		});

MyTimeline.prototype.setShowFilterBar = function(f) {
    this.setProperty("showFilterBar", f);
    if (f) {
        this._headerBar.setVisible(true);
    } else {
        this._headerBar.setVisible(false);
    }
};

MyTimeline.prototype.onBeforeRendering = function() {
    if (!this.getShowFilterBar()) {
        this._headerBar.setVisible(false);
    }
//    if (!this.getShowSearch()) {
//        this._searchField.setVisible(false);
//    }
    var i = this.getContent();
    if (this._filterChange) {
        i = this._displayItems;
    }
    this._filterChange = false;
    this._showMore = false;
    var b = this.getBindingInfo("content");
    if ((this.getGrowing() && (b) && (b.model !== sap.suite.ui.commons.Timeline.INTERNAL_MODEL_NAME) && (this.getMaxItemsCount() > this._growDisplayCount)) ) {
        if ((this._filterText === this.resBundle.getText("TIMELINE_ALL")) || (this.getEnableBackendFilter())) {
            this._showMore = true;
        }
    } else if (this.getMaxItemsCount() > 0) {
        this._iItemCount = this.getMaxItemsCount();
    }
    if (this._iItemCount == 0) {
        this._iItemCount = this.getMaxItemsCount();
        this._showMore = false;
    }
    if (this.getForceGrowing()) {
        this._showMore = true;
    }
    this.setOutput(i);
};

MyTimeline.prototype.init = function() {
    this.data("sap-ui-fastnavgroup", "true", true);
    this._previousTarget = null;
    this._previousTabPreviousTarget = null;
    this._filterTarget = null;
    this._addTarget = null;
    this._shell = null;
    this._aRows = null;
    this._aDomRefs = null;
    this._messageStrip = null;
    this._sapTabbable = null;
    this._moreButtonFound = false;
    this._pageSize = 10;
    this._addButtonFound = false;
    this._dummy = false;
    this._tabprevious = false;
    this._tabnext = false;
    this._moreTarget = null;
    this._saveTarget = null;
    this._previousShellTarget = null;
    this._previousLineItemsHeight = 0;
    this._showMorescrollLocation = 0;
    this._skipOnFocusIn = false;
    this._previousShiftF6Target = null;
    this._outFocusTarget = null;
    this._outFocusTargetClassName = null;
    this._firstTimeSetFocus = true;
    this._headerBarFieldLength = 0;
    this._renderDblSided = false;
    this._maxTimeLineWidth = 685;
    this._groupByField = 'dateTime';
    sap.m.DisplayListItem.extend("sap.suite.ui.commons.DisplayListItemWithKey", {
        metadata: {
            properties: {
                "key": {
                    type: "string",
                    defaultValue: ""
                },
            }
        },
        renderer: 'sap.m.DisplayListItemRenderer'
    });
    var t = this;
    this._prevTargetId = "";
    this._groupByField = "";
    this._internalModel = new sap.ui.model.json.JSONModel();
    this._finishLoading = true;
    this.setModel(this._internalModel, sap.suite.ui.commons.Timeline.INTERNAL_MODEL_NAME);
    var l = sap.ui.getCore().getConfiguration().getLanguage();
    this.resBundle = sap.ui.getCore().getLibraryResourceBundle("sap.suite.ui.commons", l);
    this._emptyList = new sap.m.List();
    this._emptyList.setNoDataText(this.resBundle.getText('TIMELINE_NO_DATA'));
    this._filterIcon = new sap.m.Button(this.getId() + "-filter",{
        type: sap.m.ButtonType.Transparent,
        icon: "sap-icon://filter",
        tooltip: this.resBundle.getText("TIMELINE_FILTER_BY"),
        press: function(e) {
            t._openFilterDialog();
        }
    });
    var T = new sap.m.ToolbarSpacer();
    this._headerBar = new sap.m.OverflowToolbar({
        id: this.getId() + "-filterToolBar",
        content: [T, this._filterIcon]
    });
    this._headerBar.setVisible(this.getShowFilterBar());
    this._searchField = new sap.m.SearchField(this.getId() + "-timelineSearch",{
        search: function(e) {
            var s = e.getSource().getValue();
            var b = t.getBindingInfo("content");
            var c = sap.ui.model.FilterOperator.Contains;
            var m = t.getBinding('content').oEntityType.property;
            var a = [];
            a.push(t.getBindingInfo('content').template.mBindingInfos.text.parts[0].path);
            a.push(t.getBindingInfo('content').template.mBindingInfos.title.parts[0].path);
            a.push(t.getBindingInfo('content').template.mBindingInfos.userName.parts[0].path);
            var f = new sap.ui.model.Filter(a.map(function(g) {
                return new sap.ui.model.Filter(g,c,s);
            }),false);
            t.getBinding("content").filter([f]);
        }
    });
    this._headerBar.insertContent(T, 1);
    this._headerBar.insertContent(this._filterIcon, 2);
    var t = this;
    this._messageStrip = new sap.m.MessageStrip({
        close: function(e) {
            this.setText("");
            if (t._lastHeaderBaRTabField !== null) {
                jQuery(t._lastHeaderBaRTabField).focus();
                e.preventDefault();
            }
        }
    });
    this._filterInfoText = new sap.m.Text({
        maxLines: 1,
        width: "100%"
    });
    this._headerInfoBar = new sap.m.Toolbar({
        id: this.getId() + "-filterInfoBar",
        content: [this._filterInfoText],
        design: sap.m.ToolbarDesign.Info,
        visible: false
    });
    this._customFilter = false;
    this._filterChange = false;
    this._contentChange = true;
    this._filterDialog = new sap.m.ResponsivePopover(this.getId() + "-popover_filter",{
        title: this.resBundle.getText("TIMELINE_FILTER_BY"),
        placement: sap.m.PlacementType.Auto,
        contentHeight: "15rem",
        contentWidth: "25rem"
    });
    var d = new sap.suite.ui.commons.DisplayListItemWithKey({
        key: "{key}",
        label: "{text}"
    });
    this._filterList = new sap.m.List(this.getId() + "-filterlist",{
        mode: sap.m.ListMode.SingleSelectMaster,
        items: {
            path: "/items",
            template: d
        },
        selectionChange: function(e) {
            t._searchField.setValue("");
            var f = t._filterList.getSelectedItem().getLabel();
            t._setFilterInfoText(f);
            if (t._filterList.getSelectedItem() && !t.getEnableBackendFilter()) {
                t._filterChange = true;
                t._filterText = f;
                t._resetDisplayItems(f);
            }
            t.fireFilterSelectionChange({
                selectedItem: t._filterList.getSelectedItem()
            });
            t._currentFilterKey = t._filterList.getSelectedItem().getKey();
            t._filterDialog.close();
        }
    });
    this._filterText = this.resBundle.getText("TIMELINE_ALL");
    this._growing = false;
    if (this.getGrowing()) {
        this._growDisplayCount = this.getGrowingThreshold();
        this._iItemCount = this.getGrowingThreshold();
        this._getMoreButton = new sap.m.Button(this.getId() + "-getmore",{
            text: this.resBundle.getText("TIMELINE_SHOW_MORE"),
            width: "100%",
            press: function() {
                if (document.getElementById(t.getId() + '-contentH')) {
                    t._showMorescrollLocation = document.getElementById(t.getId() + '-contentH').scrollLeft;
                } else {
                    t._showMorescrollLocation = document.getElementById(t.getId() + '-content').scrollTop;
                }
                t.fireGrow();
                t._iItemCount += t.getGrowingThreshold();
                var o = t._growDisplayCount;
                t._growDisplayCount += t.getGrowingThreshold();
                if (t.oItemNavigation) {
                    t.removeDelegate(t.oItemNavigation);
                    t.oItemNavigation.destroy();
                }
                t._startItemNavigation();
                if (t._iItemCount > t.getMaxItemsCount()) {
                    t._iItemCount = t.getMaxItemsCount();
                }
                var b = t.getBindingInfo("content");
                if (t._growing) {
                    b.startIndex = 0;
                    b.length = t._iItemCount;
                    t.getBinding("content").getContexts(0, t._iItemCount);
                } else {
                    t.rerender();
                }
                jQuery.sap.delayedCall(300, t, function() {
                    if (document.getElementById(t.getId() + '-contentH')) {
                        document.getElementById(t.getId() + '-contentH').scrollLeft = t._showMorescrollLocation;
                    } else {
                        document.getElementById(t.getId() + '-content').scrollTop = t._showMorescrollLocation;
                    }
                });
            }
        });
    }
    this._scHeight = 400;
    this._height = 0;
    this._aFilterList = [];
};