app.service("auctionService", ["$http", "$q", function($http, $q){
	return {
		products : getProducts()
	}


	function handleError( response ) {
        if (
            ! angular.isObject( response.data ) ||
            ! response.data.message
            ) {
            return( $q.reject( "An unknown error occurred." ) );
        }
        return( $q.reject( response.data.message ) );
    }
    
    function handleSuccess( response ) {
		return( response.data );
    }
}]);