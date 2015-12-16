var Comment = React.createClass(
{
	rawMarkup: function() 
	{
		var rawMarkup = marked(this.props.children.toString(), {sanitize: true});
		return { __html: rawMarkup };
	},

	render: function()
	{
		return (
				<div className="comment">
					<a href={this.props.url}>{this.props.url}</a><br/>
					<span dangerouslySetInnerHTML={this.rawMarkup()} />
				</div>
		);
	}
});

var CommentBox = React.createClass(
{
	loadCommentsFromServer: function()
	{
		$.ajax({
			url: this.props.url,
			dataType: 'json',
			cache: false,
			success: function(data) 
			{
				this.setState({data: data});
			}.bind(this),
			error: function(xhr, status, err)
			{
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	
	handleCommentSubmit: function(comment) 
	{
		var comments = this.state.data;
		var newComments = comments.concat([comment]);
		this.setState({data: newComments});
		
		$.ajax({
			url: this.props.url,
			dataType: 'json',
			type: 'POST',
			data: comment,
			success: function(data) {
				this.setState({data: data});
			}.bind(this),
			error: function(xhr, status, err) {
				this.setState({data: comments});
				console.error(this.props.url, status, err.toString());
			}.bind(this)
		});
	},
	
	getInitialState: function()
	{
		return {data: []};
	},
	
	componentDidMount: function() 
	{
		this.loadCommentsFromServer();
		setInterval(this.loadCommentsFromServer, this.props.pollInterval);
	},
	
	render: function()
	{
		return (
				<div className="commentBox">
					<CommentList data={this.state.data} />
					<CommentForm onCommentSubmit={this.handleCommentSubmit} />
				</div>
		);
	}
});

var CommentList = React.createClass(
{
	render: function()
	{
		var commentNodes = this.props.data.map(function(comment) {
			return (
					<Comment key={comment.added} url={comment.url}>
					{comment.description}
					</Comment>
			);
		});
		
		return (
				<div className="commentList">
				{commentNodes}
				</div>
		);
	}
});

var CommentForm = React.createClass(
{
	getInitialState: function() 
	{
		return {author: '', text: ''};
	},
	
	handleAuthorChange: function(e)
	{
		this.setState({author: e.target.value});
	},
	
	handleTextChange: function(e) 
	{
		this.setState({text: e.target.value});
	},
	
	handleSubmit: function(e)
	{
		e.preventDefault();
		var author = this.state.author.trim();
		var text = this.state.text.trim();
		if (!text || !author) 
			return;
		
		this.props.onCommentSubmit({url:text});
		this.setState({author: '', text: ''});
	},
	
	render: function() 
	{
		return (
				<form className="commentForm" onSubmit={this.handleSubmit}>
				<input
					type="text"
					placeholder="Your name"
					value={this.state.author}
					onChange={this.handleAuthorChange}
					className="form-control"
				/>
				<input
					type="text"
					placeholder="Say something..."
					value={this.state.text}
					onChange={this.handleTextChange}
					className="form-control"
				/>
				<input type="submit" value="Post" className="btn"/>
				</form>
		);
	}
});

ReactDOM.render(
		<CommentBox url="/LinkStore/api/links/" pollInterval={2000} />,
		document.getElementById('content')
);