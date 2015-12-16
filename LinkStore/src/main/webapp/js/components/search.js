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
					<SearchBox/>
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
	render: function()
	{
		return (
				<input 
					type="text"
					placeholder="Type to search..."
					className="form-control">
				</input>
		);
	}
});