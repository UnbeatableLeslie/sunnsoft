var app = new Vue({
	el : "#app",
	data : {
		message : "Vue"
	},
	beforeCreate : function(){
		console.log('beforeCreate')
	},
	created : function() {
		// `this` 指向 vm 实例
		console.log("created")
	},
	beforeMount:function(){
		console.log("beforeMount")
	},
	mounted:function(){
		console.log("mounted")
	},
	beforeUpdate:function(){
		console.log("beforeUpdate")
	},
	updated:function(){
		console.log("updated")
	},
	beforeDestroy:function(){
		console.log("beforeDestory")
	},
	destroyed:function(){
		console.log("destroyed")
	}
})
var app2 = new Vue({
	el : "#app2",
	data : {
		message : "页面加载于" + new Date().toLocaleString()
	}
})
var app3 = new Vue({
	el : "#app3",
	data : {
		seen : false
	}
})
var app4 = new Vue({
	el : "#app4",
	data : {
		todos : [ {
			text : "学习 Vue"
		}, {
			text : "学习 SpringBoot"
		}, {
			text : "学习 SpringCloud"
		} ]
	}
})
var app5 = new Vue({
	el : "#app5",
	data : {
		message : "Hello Vue.js!"
	},
	methods : {
		reverse : function() {
			this.message = this.message.split('').reverse().join('')
		}
	}
})
var app6 = new Vue({
	el : "#app6",
	data : {
		message : "Hello vue I am App6"
	}
})

Vue.component('todo-item', {
	props : [ 'todo' ],
	template : '<li>{{ todo.text }}</li>'
})
var app7 = new Vue({
	el : '#app7',
	data : {
		itemList : [ {
			id : 0,
			text : '蔬菜'
		}, {
			id : 1,
			text : '奶酪'
		}, {
			id : 2,
			text : '随便其它什么人吃的东西'
		} ]
	}
})
var obj = {
	foo : 'bar'
}

Object.freeze(obj)

new Vue({
	el : '#app8',
	data : obj
})
