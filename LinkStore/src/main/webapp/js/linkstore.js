

$(function()
{
	var linkForm = [
		{name:"url", type:"text", title:"URL"},
		{name:"title", type:"text", title:"Title"},
		{name:"description", type:"textarea", title:"Description"},
	];
	
	ReactDOM.render(
			<FormPanel title="New Link" controls={linkForm} url="/LinkStore/api/links"/>,
			document.getElementById('new-link')
	);
	
	ReactDOM.render(
			<LinkBox url="/LinkStore/api/links/" pollInterval={2000} />,
			document.getElementById('content')
	);
	
	ReactDOM.render(
			<SearchPanel />,
			document.getElementById('search')
	);
});