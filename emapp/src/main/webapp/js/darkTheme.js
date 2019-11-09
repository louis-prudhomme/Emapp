function setDarkMode(){
    if (!$("body").hasClass("dark"))
        $("body").addClass("dark");
        localStorage.setItem("mod","dark");
}

function setLightMode(){
    if ($("body").hasClass("dark"))
            $("body").removeClass("dark");
            localStorage.setItem("mod","light");
}

function loadPage2(){
    let mod = localStorage.getItem("mod");
    if(mod){
        $("body").addClass(mod);
    }
}