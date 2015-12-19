/* The Form Component */
/*
 *  *--------------------------------*
 *  |Form Title                      |
 *  |                                |
 *  | *-------------------------*    |
 *  | | Form Control            |    |
 *  | | Label                   |    |
 *  | | *-------------------*   |    |
 *  | | | Input Element         |    |
 *  | | *-------------------*   |    |
 *  | *-------------------------*    |
 *  |                                |
 *  *--------------------------------*
 */

/**
 * The mother of all components in the form.
 */
window.FormPanel = React.createClass(
{
	render: function()
	{
		return (
				<div className="panel panel-default">
					<div className="panel-heading">{this.props.title}</div>
					<div className="panel-body">
						<FormContent controls={this.props.controls} url={this.props.url} />
					</div>
				</div>
		);
	}
});


/**
 * A plain div containing all the control elements
 */
window.FormContent = React.createClass(
{
	formId: new Date().getTime(),
	
	handleChange: function(event)
	{
		var state = {};
		state[event.target.id] = event.target.value;
		this.setState(state);
	},
	
	handleSubmit: function(event)
	{
		event.preventDefault();
		var self = this;
		
		$.ajax({
			url:		this.props.url,
			dataType:	'json',
			type:		'POST',
			data:		this.state,
			success:	function(data)
			{
				self.replaceState({});
				$("[data-form-id=" + self.formId + "]").val("");
			}.bind(this),
			error:		function(xhr, status, err)
			{
				/*this.setState({data: comments});
				console.error(this.props.url, status, err.toString());*/
			}.bind(this)
		});
		
		return false;
	},
	
	render: function()
	{
		var self = this;
		var controlList = this.props.controls.map(function(control)
		{
			return (
					<FormControl 
						onChange={self.handleChange} 
						key={control.name} 
						control={control}
						formId={self.formId}/>
			);
		});

		return (<form onSubmit={this.handleSubmit}>
					{controlList}
					<input className="btn btn-primary pull-right" type="submit" value="Add" />
				</form>
		);
	}
});

/**
 * A class to render a control based on the type
 */
window.FormControl = React.createClass(
{
	text: function()
	{
		return (
			<div className="form-group">
				<input 
					onChange={this.props.onChange}
					type={this.props.control.type} 
					className="form-control" 
					id={this.props.control.name}
					placeholder={this.props.control.title} 
					data-form-id={this.props.formId} />
			</div>
		);
	},
	textarea: function()
	{
		return (
				<div className="form-group">
					<textarea 
						onChange={this.props.onChange}
						className="form-control" 
						id={this.props.control.name}
						placeholder={this.props.control.title}
						data-form-id={this.props.formId} ></textarea>
				</div>
			);
	},
	render: function()
	{
		return this[this.props.control.type]();
	}
});