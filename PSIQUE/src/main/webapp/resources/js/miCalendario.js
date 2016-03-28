
$(function () {
    
    //Ejemplos
    
    $('#basic').timepicker();
    $('#current').timepicker();
    $('#setTimeButton').on('click', function () {
        $('#current').timepicker('setTime', new Date());
    });
    $('#duracion').timepicker({
        'minTime': '2:00pm',
        'maxTime': '11:30pm',
        'showDuration': true
    });
    $('#disable').timepicker({
        'disableTimeRanges': [
            ['1am', '2am'],
            ['3am', '4:01am']
        ]
    });
    $('#step').timepicker({'step': 30});
    
    //Calendarios limitados
    
    $('#calendarioMañana').timepicker({
        'step': 30,
        'minTime': '9:30 am',
        'maxTime': '11:30 am'
    });
    
});