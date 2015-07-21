define(["dojo/_base/declare"],
 function(declare) {
	return declare([], {
		_dwlmLogDepth: 0,
		_dwlmLogStart: function() {
			var phase = arguments.callee.caller.nom;		
			console.log(this.declaredClass, ": " + this._dwlmPrefix() + "<" + phase +">");
			this._dwlmLogDepth++;
		},
		_dwlmLogEnd: function() {
			var phase = arguments.callee.caller.nom;		
			this._dwlmLogDepth--;
			console.log(this.declaredClass, ": " + this._dwlmPrefix() + "</" + phase +">");
		},
		_dwlmPrefix: function() {
			var res = "", i;
			for (i = 0; i < this._dwlmLogDepth * 2; i += 2) {
				res += "  ";
			}
			return res;
		},		
		preamble: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		
		constructor: function() {			
			this._dwlmLogStart();
			// .ctor automatic chaining
			this._dwlmLogEnd();
		},

		postscript: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		
		create: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		
		postMixInProperties: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		
		buildRendering: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		
		postCreate: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		
		startup: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		
		destroyRecursive: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		
		destroy: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		
		uninitialize: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		
		destroyRendering: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		},
		destroyDescendants: function() {
			this._dwlmLogStart();
			this.inherited(arguments);
			this._dwlmLogEnd();
		}		
	});
});