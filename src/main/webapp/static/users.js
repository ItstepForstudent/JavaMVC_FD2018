class Ajax{
    static get(url,onsuccess,onerror){
        let request = new XMLHttpRequest();
        request.open("GET",url,true);
        request.onreadystatechange=function () {
            if(request.readyState!==4) return;
            if(request.status===200)
                onsuccess(request.responseText);
            else if(onerror)
                onerror(request.status,request.statusText);
        };
        request.send();
    }
    static post(url,params,onsuccess,onerror){
        let request = new XMLHttpRequest();
        request.open("POST",url,true);
        request.setRequestHeader("Content-Type","application/x-www-form-urlencoded");
        request.onreadystatechange=function () {
            if(request.readyState!==4) return;
            if(request.status===200)
                onsuccess(request.responseText);
            else if(onerror)
                onerror(request.status,request.statusText);
        };
        let _params = [];
        for(let i in params) _params.push(i+"="+encodeURIComponent(params[i]));
        request.send(_params.join("&"));
    }
    static postMultiPart(url,params,onsuccess,onerror){
        let request = new XMLHttpRequest();
        request.open("POST",url,true);
        request.onreadystatechange=function () {
            if(request.readyState!==4) return;
            if(request.status===200)
                onsuccess(request.responseText);
            else if(onerror)
                onerror(request.status,request.statusText);
        };
        request.send(params);
    }
}


let page = {

    init:function () {
        this.container = document.querySelector(".users");
        this.usersView = this.container.querySelector(".list");
        this.userNameInp = this.container.querySelector("input");
        this.userAddBtn = this.container.querySelector("button");
        this.attachEvents();
        this.updateUsers();
    },
    attachEvents: function () {
        this.userAddBtn.addEventListener("click",()=>this.addUser());
        this.container.addEventListener("click",e=>{
            if(!e.target.matches(".del"))return;
            let id = e.target.dataset.id;
            Ajax.post("/mvc/users/del",{id:id},r=>this.updateUsers());
        })
    },
    updateUsers(){
        Ajax.get("/mvc/users/all",(resp)=> {
            var data = JSON.parse(resp);
            var users = data.data;
            this.usersView.innerHTML="";
            users.forEach(u=>{
                this.usersView.insertAdjacentHTML("beforeEnd",`<div>${u.name} <div class="del" style="color: red" data-id="${u.id}">del</div>`)
            })

        })
    },

    addUser(){
        let name = this.userNameInp.value;
        Ajax.post("/mvc/users/add",{name:name},resp=>{
            this.updateUsers();
        })
    }
};

window.addEventListener("load",()=>page.init());