/**
 * Created by mehdithreem on 1/10/2018 AD.
 */

$(document).ready(function(){
    var api = "/spark/memleak";

    var initialLeakRate = "$leakRate";
    console.log(initialLeakRate);

    var setRate = function (newRate) {
        $.ajax({
            url: api + "/rate",
            data: {
                "rate": newRate
            },
            type: 'post',
            cache: false
        });
    };

    $('#leak-rate').range({
        min: 0,
        max: 5,
        start: +initialLeakRate,
        step: 0.1,
        input: '#leak-rate-input',
        onChange: function(value) {
            setRate(value);
        }
    });

    $('#leak-size').range({
        min: 0,
        max: 1024*10,
        start: 1024,
        step: 256,
        input: '#leak-size-input',
        onChange: function(value) {
            // $('#leak-size-input').html(value);
        }
    });
});