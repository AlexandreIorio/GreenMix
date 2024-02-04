window.update = async function() {
    let obj = document.getElementById('obj-garden');
    obj.contentWindow.update();
    parent.window.update();
};