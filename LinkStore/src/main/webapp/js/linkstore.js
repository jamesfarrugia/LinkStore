$(function()
{
	ReactDOM.render(
			<CommentBox url="/LinkStore/api/links/" pollInterval={2000} />,
			document.getElementById('content')
	);
	
	ReactDOM.render(
			<SearchPanel />,
			document.getElementById('search')
	);
});