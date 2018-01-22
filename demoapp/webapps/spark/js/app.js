/**
 * Created by mehdithreem on 1/10/2018 AD.
 */

$(document).ready(function(){
    $('#range-3').range({
        min: 0,
        max: 10,
        start: 5,
        onChange: function(value) {
            console.log(value);
        }
    });
});