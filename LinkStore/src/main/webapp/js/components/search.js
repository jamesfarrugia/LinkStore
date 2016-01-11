/* The Search Component */
/*
 *  *--------------------*
 *  | Type to search...  |
 *  *--------------------*
 * 
 *  *--------------------------------*
 *  |Results                         |
 *  |                                |
 *  | links matching 'term'          |
 *  | *-------------------------*    |
 *  | | Search result list      |    |
 *  | | *-------------------*   |    |
 *  | | | Search result     |   |    |
 *  | | *-------------------*   |    |
 *  | | *-------------------*   |    |
 *  | | | Search result     |   |    |
 *  | | *-------------------*   |    |
 *  | *-------------------------*    |
 *  |                                |
 *  *--------------------------------*
 */

/**
 * The mother of all components in the search.
 */
window.SearchPanel = React.createClass(
{
	render: function()
	{
		return (
				<div className="search-panel">
					<SearchBox pollInterval={250}/>
					<hr/>
				</div>
		);
	}
});

/**
 * The input area where the user types in their term
 */
window.SearchBox = React.createClass(
{
	handleSearchTerm: function(event)
	{
		var state = {};
		state.term = event.target.value;
		
		/* set a flag to reload during the next loop */
		if (!this.state.term || this.state.term != state.term)
			this.setState({ripe: true});
		
		this.setState(state);
	},
	
	testSearch : function()
	{
		if (this.state.ripe)
		{
			console.log("ripe for " + this.state.term);
			this.setState({ripe: false});
		}
		
		window.store.searching = this.state.term && this.state.term != "";
		
	},
	
	getInitialState: function()
	{
		return {};
	},
	
	componentDidMount: function() 
	{
		setInterval(this.testSearch, this.props.pollInterval);
	},
	
	render: function()
	{
		return (
				<input 
					type="text"
					placeholder="Type to search..."
					className="form-control"
					onChange={this.handleSearchTerm}>
				</input>
		);
	}
});