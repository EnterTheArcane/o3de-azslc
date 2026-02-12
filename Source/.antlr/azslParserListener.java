// Generated from d:/O3DE/AZSLC/Source/azslParser.g4 by ANTLR 4.13.1
import org.antlr.v4.runtime.tree.ParseTreeListener;

/**
 * This interface defines a complete listener for a parse tree produced by
 * {@link azslParser}.
 */
public interface azslParserListener extends ParseTreeListener {
	/**
	 * Enter a parse tree produced by {@link azslParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void enterCompilationUnit(azslParser.CompilationUnitContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#compilationUnit}.
	 * @param ctx the parse tree
	 */
	void exitCompilationUnit(azslParser.CompilationUnitContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#topLevelDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterTopLevelDeclaration(azslParser.TopLevelDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#topLevelDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitTopLevelDeclaration(azslParser.TopLevelDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#idExpression}.
	 * @param ctx the parse tree
	 */
	void enterIdExpression(azslParser.IdExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#idExpression}.
	 * @param ctx the parse tree
	 */
	void exitIdExpression(azslParser.IdExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#unqualifiedId}.
	 * @param ctx the parse tree
	 */
	void enterUnqualifiedId(azslParser.UnqualifiedIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#unqualifiedId}.
	 * @param ctx the parse tree
	 */
	void exitUnqualifiedId(azslParser.UnqualifiedIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#qualifiedId}.
	 * @param ctx the parse tree
	 */
	void enterQualifiedId(azslParser.QualifiedIdContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#qualifiedId}.
	 * @param ctx the parse tree
	 */
	void exitQualifiedId(azslParser.QualifiedIdContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#nestedNameSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterNestedNameSpecifier(azslParser.NestedNameSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#nestedNameSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitNestedNameSpecifier(azslParser.NestedNameSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#classDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void enterClassDefinitionStatement(azslParser.ClassDefinitionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#classDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void exitClassDefinitionStatement(azslParser.ClassDefinitionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void enterClassDefinition(azslParser.ClassDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#classDefinition}.
	 * @param ctx the parse tree
	 */
	void exitClassDefinition(azslParser.ClassDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#baseList}.
	 * @param ctx the parse tree
	 */
	void enterBaseList(azslParser.BaseListContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#baseList}.
	 * @param ctx the parse tree
	 */
	void exitBaseList(azslParser.BaseListContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#classMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterClassMemberDeclaration(azslParser.ClassMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#classMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitClassMemberDeclaration(azslParser.ClassMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#structDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void enterStructDefinitionStatement(azslParser.StructDefinitionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#structDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void exitStructDefinitionStatement(azslParser.StructDefinitionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#structDefinition}.
	 * @param ctx the parse tree
	 */
	void enterStructDefinition(azslParser.StructDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#structDefinition}.
	 * @param ctx the parse tree
	 */
	void exitStructDefinition(azslParser.StructDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#structMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterStructMemberDeclaration(azslParser.StructMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#structMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitStructMemberDeclaration(azslParser.StructMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#anyStructuredTypeDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void enterAnyStructuredTypeDefinitionStatement(azslParser.AnyStructuredTypeDefinitionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#anyStructuredTypeDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void exitAnyStructuredTypeDefinitionStatement(azslParser.AnyStructuredTypeDefinitionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#enumDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void enterEnumDefinitionStatement(azslParser.EnumDefinitionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#enumDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void exitEnumDefinitionStatement(azslParser.EnumDefinitionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#enumDefinition}.
	 * @param ctx the parse tree
	 */
	void enterEnumDefinition(azslParser.EnumDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#enumDefinition}.
	 * @param ctx the parse tree
	 */
	void exitEnumDefinition(azslParser.EnumDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code UnscopedEnum}
	 * labeled alternative in {@link azslParser#enumKey}.
	 * @param ctx the parse tree
	 */
	void enterUnscopedEnum(azslParser.UnscopedEnumContext ctx);
	/**
	 * Exit a parse tree produced by the {@code UnscopedEnum}
	 * labeled alternative in {@link azslParser#enumKey}.
	 * @param ctx the parse tree
	 */
	void exitUnscopedEnum(azslParser.UnscopedEnumContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ScopedEnum}
	 * labeled alternative in {@link azslParser#enumKey}.
	 * @param ctx the parse tree
	 */
	void enterScopedEnum(azslParser.ScopedEnumContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ScopedEnum}
	 * labeled alternative in {@link azslParser#enumKey}.
	 * @param ctx the parse tree
	 */
	void exitScopedEnum(azslParser.ScopedEnumContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#enumeratorListDefinition}.
	 * @param ctx the parse tree
	 */
	void enterEnumeratorListDefinition(azslParser.EnumeratorListDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#enumeratorListDefinition}.
	 * @param ctx the parse tree
	 */
	void exitEnumeratorListDefinition(azslParser.EnumeratorListDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#enumeratorDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterEnumeratorDeclarator(azslParser.EnumeratorDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#enumeratorDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitEnumeratorDeclarator(azslParser.EnumeratorDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#anyStructuredTypeDefinition}.
	 * @param ctx the parse tree
	 */
	void enterAnyStructuredTypeDefinition(azslParser.AnyStructuredTypeDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#anyStructuredTypeDefinition}.
	 * @param ctx the parse tree
	 */
	void exitAnyStructuredTypeDefinition(azslParser.AnyStructuredTypeDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#interfaceDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDefinitionStatement(azslParser.InterfaceDefinitionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#interfaceDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDefinitionStatement(azslParser.InterfaceDefinitionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#interfaceDefinition}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceDefinition(azslParser.InterfaceDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#interfaceDefinition}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceDefinition(azslParser.InterfaceDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterInterfaceMemberDeclaration(azslParser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#interfaceMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitInterfaceMemberDeclaration(azslParser.InterfaceMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#constantBufferTemplated}.
	 * @param ctx the parse tree
	 */
	void enterConstantBufferTemplated(azslParser.ConstantBufferTemplatedContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#constantBufferTemplated}.
	 * @param ctx the parse tree
	 */
	void exitConstantBufferTemplated(azslParser.ConstantBufferTemplatedContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#variableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarationStatement(azslParser.VariableDeclarationStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#variableDeclarationStatement}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarationStatement(azslParser.VariableDeclarationStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#functionParams}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParams(azslParser.FunctionParamsContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#functionParams}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParams(azslParser.FunctionParamsContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#functionParam}.
	 * @param ctx the parse tree
	 */
	void enterFunctionParam(azslParser.FunctionParamContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#functionParam}.
	 * @param ctx the parse tree
	 */
	void exitFunctionParam(azslParser.FunctionParamContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#hlslSemantic}.
	 * @param ctx the parse tree
	 */
	void enterHlslSemantic(azslParser.HlslSemanticContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#hlslSemantic}.
	 * @param ctx the parse tree
	 */
	void exitHlslSemantic(azslParser.HlslSemanticContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#hlslSemanticName}.
	 * @param ctx the parse tree
	 */
	void enterHlslSemanticName(azslParser.HlslSemanticNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#hlslSemanticName}.
	 * @param ctx the parse tree
	 */
	void exitHlslSemanticName(azslParser.HlslSemanticNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#attributeArguments}.
	 * @param ctx the parse tree
	 */
	void enterAttributeArguments(azslParser.AttributeArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#attributeArguments}.
	 * @param ctx the parse tree
	 */
	void exitAttributeArguments(azslParser.AttributeArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#attributeArgumentList}.
	 * @param ctx the parse tree
	 */
	void enterAttributeArgumentList(azslParser.AttributeArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#attributeArgumentList}.
	 * @param ctx the parse tree
	 */
	void exitAttributeArgumentList(azslParser.AttributeArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by the {@code GlobalAttribute}
	 * labeled alternative in {@link azslParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterGlobalAttribute(azslParser.GlobalAttributeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code GlobalAttribute}
	 * labeled alternative in {@link azslParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitGlobalAttribute(azslParser.GlobalAttributeContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AttachedAttribute}
	 * labeled alternative in {@link azslParser#attribute}.
	 * @param ctx the parse tree
	 */
	void enterAttachedAttribute(azslParser.AttachedAttributeContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AttachedAttribute}
	 * labeled alternative in {@link azslParser#attribute}.
	 * @param ctx the parse tree
	 */
	void exitAttachedAttribute(azslParser.AttachedAttributeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#attributeSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterAttributeSpecifier(azslParser.AttributeSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#attributeSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitAttributeSpecifier(azslParser.AttributeSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#attributeSpecifierSequence}.
	 * @param ctx the parse tree
	 */
	void enterAttributeSpecifierSequence(azslParser.AttributeSpecifierSequenceContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#attributeSpecifierSequence}.
	 * @param ctx the parse tree
	 */
	void exitAttributeSpecifierSequence(azslParser.AttributeSpecifierSequenceContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#attributeSpecifierAny}.
	 * @param ctx the parse tree
	 */
	void enterAttributeSpecifierAny(azslParser.AttributeSpecifierAnyContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#attributeSpecifierAny}.
	 * @param ctx the parse tree
	 */
	void exitAttributeSpecifierAny(azslParser.AttributeSpecifierAnyContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#block}.
	 * @param ctx the parse tree
	 */
	void enterBlock(azslParser.BlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#block}.
	 * @param ctx the parse tree
	 */
	void exitBlock(azslParser.BlockContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#statement}.
	 * @param ctx the parse tree
	 */
	void enterStatement(azslParser.StatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#statement}.
	 * @param ctx the parse tree
	 */
	void exitStatement(azslParser.StatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#forInitializer}.
	 * @param ctx the parse tree
	 */
	void enterForInitializer(azslParser.ForInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#forInitializer}.
	 * @param ctx the parse tree
	 */
	void exitForInitializer(azslParser.ForInitializerContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CaseSwitchLabel}
	 * labeled alternative in {@link azslParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterCaseSwitchLabel(azslParser.CaseSwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CaseSwitchLabel}
	 * labeled alternative in {@link azslParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitCaseSwitchLabel(azslParser.CaseSwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DefaultSwitchLabel}
	 * labeled alternative in {@link azslParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void enterDefaultSwitchLabel(azslParser.DefaultSwitchLabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DefaultSwitchLabel}
	 * labeled alternative in {@link azslParser#switchLabel}.
	 * @param ctx the parse tree
	 */
	void exitDefaultSwitchLabel(azslParser.DefaultSwitchLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#switchSection}.
	 * @param ctx the parse tree
	 */
	void enterSwitchSection(azslParser.SwitchSectionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#switchSection}.
	 * @param ctx the parse tree
	 */
	void exitSwitchSection(azslParser.SwitchSectionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#switchBlock}.
	 * @param ctx the parse tree
	 */
	void enterSwitchBlock(azslParser.SwitchBlockContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#switchBlock}.
	 * @param ctx the parse tree
	 */
	void exitSwitchBlock(azslParser.SwitchBlockContext ctx);
	/**
	 * Enter a parse tree produced by the {@code EmptyStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterEmptyStatement(azslParser.EmptyStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code EmptyStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitEmptyStatement(azslParser.EmptyStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BlockStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterBlockStatement(azslParser.BlockStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BlockStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitBlockStatement(azslParser.BlockStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExpressionStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterExpressionStatement(azslParser.ExpressionStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExpressionStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitExpressionStatement(azslParser.ExpressionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterIfStatement(azslParser.IfStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IfStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitIfStatement(azslParser.IfStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code SwitchStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterSwitchStatement(azslParser.SwitchStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code SwitchStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitSwitchStatement(azslParser.SwitchStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code WhileStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterWhileStatement(azslParser.WhileStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code WhileStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitWhileStatement(azslParser.WhileStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DoStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterDoStatement(azslParser.DoStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DoStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitDoStatement(azslParser.DoStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ForStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterForStatement(azslParser.ForStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ForStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitForStatement(azslParser.ForStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BreakStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterBreakStatement(azslParser.BreakStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BreakStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitBreakStatement(azslParser.BreakStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ContinueStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterContinueStatement(azslParser.ContinueStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ContinueStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitContinueStatement(azslParser.ContinueStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code DiscardStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterDiscardStatement(azslParser.DiscardStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code DiscardStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitDiscardStatement(azslParser.DiscardStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterReturnStatement(azslParser.ReturnStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ReturnStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitReturnStatement(azslParser.ReturnStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ExtenstionStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterExtenstionStatement(azslParser.ExtenstionStatementContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ExtenstionStatement}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitExtenstionStatement(azslParser.ExtenstionStatementContext ctx);
	/**
	 * Enter a parse tree produced by the {@code TypeAliasingDefinitionStatementLabel}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void enterTypeAliasingDefinitionStatementLabel(azslParser.TypeAliasingDefinitionStatementLabelContext ctx);
	/**
	 * Exit a parse tree produced by the {@code TypeAliasingDefinitionStatementLabel}
	 * labeled alternative in {@link azslParser#embeddedStatement}.
	 * @param ctx the parse tree
	 */
	void exitTypeAliasingDefinitionStatementLabel(azslParser.TypeAliasingDefinitionStatementLabelContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void enterElseClause(azslParser.ElseClauseContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#elseClause}.
	 * @param ctx the parse tree
	 */
	void exitElseClause(azslParser.ElseClauseContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterParenthesizedExpression(azslParser.ParenthesizedExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ParenthesizedExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitParenthesizedExpression(azslParser.ParenthesizedExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code MemberAccessExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterMemberAccessExpression(azslParser.MemberAccessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code MemberAccessExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitMemberAccessExpression(azslParser.MemberAccessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PrefixUnaryExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryExpression(azslParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PrefixUnaryExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryExpression(azslParser.PrefixUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterLiteralExpression(azslParser.LiteralExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code LiteralExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitLiteralExpression(azslParser.LiteralExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ConditionalExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterConditionalExpression(azslParser.ConditionalExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ConditionalExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitConditionalExpression(azslParser.ConditionalExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code PostfixUnaryExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnaryExpression(azslParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code PostfixUnaryExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnaryExpression(azslParser.PostfixUnaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code NumericConstructorExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterNumericConstructorExpression(azslParser.NumericConstructorExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code NumericConstructorExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitNumericConstructorExpression(azslParser.NumericConstructorExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code FunctionCallExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterFunctionCallExpression(azslParser.FunctionCallExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code FunctionCallExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitFunctionCallExpression(azslParser.FunctionCallExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code IdentifierExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterIdentifierExpression(azslParser.IdentifierExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code IdentifierExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitIdentifierExpression(azslParser.IdentifierExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code BinaryExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterBinaryExpression(azslParser.BinaryExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code BinaryExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitBinaryExpression(azslParser.BinaryExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code AssignmentExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentExpression(azslParser.AssignmentExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code AssignmentExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentExpression(azslParser.AssignmentExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CastExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterCastExpression(azslParser.CastExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CastExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitCastExpression(azslParser.CastExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code ArrayAccessExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void enterArrayAccessExpression(azslParser.ArrayAccessExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code ArrayAccessExpression}
	 * labeled alternative in {@link azslParser#expression}.
	 * @param ctx the parse tree
	 */
	void exitArrayAccessExpression(azslParser.ArrayAccessExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code OtherExpression}
	 * labeled alternative in {@link azslParser#expressionExt}.
	 * @param ctx the parse tree
	 */
	void enterOtherExpression(azslParser.OtherExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code OtherExpression}
	 * labeled alternative in {@link azslParser#expressionExt}.
	 * @param ctx the parse tree
	 */
	void exitOtherExpression(azslParser.OtherExpressionContext ctx);
	/**
	 * Enter a parse tree produced by the {@code CommaExpression}
	 * labeled alternative in {@link azslParser#expressionExt}.
	 * @param ctx the parse tree
	 */
	void enterCommaExpression(azslParser.CommaExpressionContext ctx);
	/**
	 * Exit a parse tree produced by the {@code CommaExpression}
	 * labeled alternative in {@link azslParser#expressionExt}.
	 * @param ctx the parse tree
	 */
	void exitCommaExpression(azslParser.CommaExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#postfixUnaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterPostfixUnaryOperator(azslParser.PostfixUnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#postfixUnaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitPostfixUnaryOperator(azslParser.PostfixUnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#prefixUnaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterPrefixUnaryOperator(azslParser.PrefixUnaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#prefixUnaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitPrefixUnaryOperator(azslParser.PrefixUnaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#binaryOperator}.
	 * @param ctx the parse tree
	 */
	void enterBinaryOperator(azslParser.BinaryOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#binaryOperator}.
	 * @param ctx the parse tree
	 */
	void exitBinaryOperator(azslParser.BinaryOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void enterAssignmentOperator(azslParser.AssignmentOperatorContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#assignmentOperator}.
	 * @param ctx the parse tree
	 */
	void exitAssignmentOperator(azslParser.AssignmentOperatorContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void enterArgumentList(azslParser.ArgumentListContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#argumentList}.
	 * @param ctx the parse tree
	 */
	void exitArgumentList(azslParser.ArgumentListContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#arguments}.
	 * @param ctx the parse tree
	 */
	void enterArguments(azslParser.ArgumentsContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#arguments}.
	 * @param ctx the parse tree
	 */
	void exitArguments(azslParser.ArgumentsContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclaration(azslParser.VariableDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#variableDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclaration(azslParser.VariableDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void enterVariableDeclarators(azslParser.VariableDeclaratorsContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#variableDeclarators}.
	 * @param ctx the parse tree
	 */
	void exitVariableDeclarators(azslParser.VariableDeclaratorsContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#unnamedVariableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterUnnamedVariableDeclarator(azslParser.UnnamedVariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#unnamedVariableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitUnnamedVariableDeclarator(azslParser.UnnamedVariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#namedVariableDeclarator}.
	 * @param ctx the parse tree
	 */
	void enterNamedVariableDeclarator(azslParser.NamedVariableDeclaratorContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#namedVariableDeclarator}.
	 * @param ctx the parse tree
	 */
	void exitNamedVariableDeclarator(azslParser.NamedVariableDeclaratorContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterVariableInitializer(azslParser.VariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#variableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitVariableInitializer(azslParser.VariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#standardVariableInitializer}.
	 * @param ctx the parse tree
	 */
	void enterStandardVariableInitializer(azslParser.StandardVariableInitializerContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#standardVariableInitializer}.
	 * @param ctx the parse tree
	 */
	void exitStandardVariableInitializer(azslParser.StandardVariableInitializerContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#arrayElementInitializers}.
	 * @param ctx the parse tree
	 */
	void enterArrayElementInitializers(azslParser.ArrayElementInitializersContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#arrayElementInitializers}.
	 * @param ctx the parse tree
	 */
	void exitArrayElementInitializers(azslParser.ArrayElementInitializersContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#arrayRankSpecifier}.
	 * @param ctx the parse tree
	 */
	void enterArrayRankSpecifier(azslParser.ArrayRankSpecifierContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#arrayRankSpecifier}.
	 * @param ctx the parse tree
	 */
	void exitArrayRankSpecifier(azslParser.ArrayRankSpecifierContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#packOffsetNode}.
	 * @param ctx the parse tree
	 */
	void enterPackOffsetNode(azslParser.PackOffsetNodeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#packOffsetNode}.
	 * @param ctx the parse tree
	 */
	void exitPackOffsetNode(azslParser.PackOffsetNodeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#storageFlags}.
	 * @param ctx the parse tree
	 */
	void enterStorageFlags(azslParser.StorageFlagsContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#storageFlags}.
	 * @param ctx the parse tree
	 */
	void exitStorageFlags(azslParser.StorageFlagsContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#storageFlag}.
	 * @param ctx the parse tree
	 */
	void enterStorageFlag(azslParser.StorageFlagContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#storageFlag}.
	 * @param ctx the parse tree
	 */
	void exitStorageFlag(azslParser.StorageFlagContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#type}.
	 * @param ctx the parse tree
	 */
	void enterType(azslParser.TypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#type}.
	 * @param ctx the parse tree
	 */
	void exitType(azslParser.TypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#predefinedType}.
	 * @param ctx the parse tree
	 */
	void enterPredefinedType(azslParser.PredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#predefinedType}.
	 * @param ctx the parse tree
	 */
	void exitPredefinedType(azslParser.PredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#subobjectType}.
	 * @param ctx the parse tree
	 */
	void enterSubobjectType(azslParser.SubobjectTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#subobjectType}.
	 * @param ctx the parse tree
	 */
	void exitSubobjectType(azslParser.SubobjectTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#otherViewResourceType}.
	 * @param ctx the parse tree
	 */
	void enterOtherViewResourceType(azslParser.OtherViewResourceTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#otherViewResourceType}.
	 * @param ctx the parse tree
	 */
	void exitOtherViewResourceType(azslParser.OtherViewResourceTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#rtxBuiltInTypes}.
	 * @param ctx the parse tree
	 */
	void enterRtxBuiltInTypes(azslParser.RtxBuiltInTypesContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#rtxBuiltInTypes}.
	 * @param ctx the parse tree
	 */
	void exitRtxBuiltInTypes(azslParser.RtxBuiltInTypesContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#bufferPredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterBufferPredefinedType(azslParser.BufferPredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#bufferPredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitBufferPredefinedType(azslParser.BufferPredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#bufferType}.
	 * @param ctx the parse tree
	 */
	void enterBufferType(azslParser.BufferTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#bufferType}.
	 * @param ctx the parse tree
	 */
	void exitBufferType(azslParser.BufferTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#byteAddressBufferTypes}.
	 * @param ctx the parse tree
	 */
	void enterByteAddressBufferTypes(azslParser.ByteAddressBufferTypesContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#byteAddressBufferTypes}.
	 * @param ctx the parse tree
	 */
	void exitByteAddressBufferTypes(azslParser.ByteAddressBufferTypesContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#patchPredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterPatchPredefinedType(azslParser.PatchPredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#patchPredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitPatchPredefinedType(azslParser.PatchPredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#patchType}.
	 * @param ctx the parse tree
	 */
	void enterPatchType(azslParser.PatchTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#patchType}.
	 * @param ctx the parse tree
	 */
	void exitPatchType(azslParser.PatchTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#samplerStatePredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterSamplerStatePredefinedType(azslParser.SamplerStatePredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#samplerStatePredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitSamplerStatePredefinedType(azslParser.SamplerStatePredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#scalarType}.
	 * @param ctx the parse tree
	 */
	void enterScalarType(azslParser.ScalarTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#scalarType}.
	 * @param ctx the parse tree
	 */
	void exitScalarType(azslParser.ScalarTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#streamOutputPredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterStreamOutputPredefinedType(azslParser.StreamOutputPredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#streamOutputPredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitStreamOutputPredefinedType(azslParser.StreamOutputPredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#streamOutputObjectType}.
	 * @param ctx the parse tree
	 */
	void enterStreamOutputObjectType(azslParser.StreamOutputObjectTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#streamOutputObjectType}.
	 * @param ctx the parse tree
	 */
	void exitStreamOutputObjectType(azslParser.StreamOutputObjectTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#structuredBufferPredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterStructuredBufferPredefinedType(azslParser.StructuredBufferPredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#structuredBufferPredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitStructuredBufferPredefinedType(azslParser.StructuredBufferPredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#structuredBufferName}.
	 * @param ctx the parse tree
	 */
	void enterStructuredBufferName(azslParser.StructuredBufferNameContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#structuredBufferName}.
	 * @param ctx the parse tree
	 */
	void exitStructuredBufferName(azslParser.StructuredBufferNameContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#textureType}.
	 * @param ctx the parse tree
	 */
	void enterTextureType(azslParser.TextureTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#textureType}.
	 * @param ctx the parse tree
	 */
	void exitTextureType(azslParser.TextureTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#texturePredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterTexturePredefinedType(azslParser.TexturePredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#texturePredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitTexturePredefinedType(azslParser.TexturePredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#genericTexturePredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterGenericTexturePredefinedType(azslParser.GenericTexturePredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#genericTexturePredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitGenericTexturePredefinedType(azslParser.GenericTexturePredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#textureTypeMS}.
	 * @param ctx the parse tree
	 */
	void enterTextureTypeMS(azslParser.TextureTypeMSContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#textureTypeMS}.
	 * @param ctx the parse tree
	 */
	void exitTextureTypeMS(azslParser.TextureTypeMSContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#msTexturePredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterMsTexturePredefinedType(azslParser.MsTexturePredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#msTexturePredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitMsTexturePredefinedType(azslParser.MsTexturePredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#subpassInputType}.
	 * @param ctx the parse tree
	 */
	void enterSubpassInputType(azslParser.SubpassInputTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#subpassInputType}.
	 * @param ctx the parse tree
	 */
	void exitSubpassInputType(azslParser.SubpassInputTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#subpassInputPredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterSubpassInputPredefinedType(azslParser.SubpassInputPredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#subpassInputPredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitSubpassInputPredefinedType(azslParser.SubpassInputPredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#genericSubpassInputPredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterGenericSubpassInputPredefinedType(azslParser.GenericSubpassInputPredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#genericSubpassInputPredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitGenericSubpassInputPredefinedType(azslParser.GenericSubpassInputPredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#vectorType}.
	 * @param ctx the parse tree
	 */
	void enterVectorType(azslParser.VectorTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#vectorType}.
	 * @param ctx the parse tree
	 */
	void exitVectorType(azslParser.VectorTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#genericVectorType}.
	 * @param ctx the parse tree
	 */
	void enterGenericVectorType(azslParser.GenericVectorTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#genericVectorType}.
	 * @param ctx the parse tree
	 */
	void exitGenericVectorType(azslParser.GenericVectorTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#scalarOrVectorType}.
	 * @param ctx the parse tree
	 */
	void enterScalarOrVectorType(azslParser.ScalarOrVectorTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#scalarOrVectorType}.
	 * @param ctx the parse tree
	 */
	void exitScalarOrVectorType(azslParser.ScalarOrVectorTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#scalarOrVectorOrMatrixType}.
	 * @param ctx the parse tree
	 */
	void enterScalarOrVectorOrMatrixType(azslParser.ScalarOrVectorOrMatrixTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#scalarOrVectorOrMatrixType}.
	 * @param ctx the parse tree
	 */
	void exitScalarOrVectorOrMatrixType(azslParser.ScalarOrVectorOrMatrixTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#matrixType}.
	 * @param ctx the parse tree
	 */
	void enterMatrixType(azslParser.MatrixTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#matrixType}.
	 * @param ctx the parse tree
	 */
	void exitMatrixType(azslParser.MatrixTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#genericMatrixPredefinedType}.
	 * @param ctx the parse tree
	 */
	void enterGenericMatrixPredefinedType(azslParser.GenericMatrixPredefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#genericMatrixPredefinedType}.
	 * @param ctx the parse tree
	 */
	void exitGenericMatrixPredefinedType(azslParser.GenericMatrixPredefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#registerAllocation}.
	 * @param ctx the parse tree
	 */
	void enterRegisterAllocation(azslParser.RegisterAllocationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#registerAllocation}.
	 * @param ctx the parse tree
	 */
	void exitRegisterAllocation(azslParser.RegisterAllocationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#samplerStateProperty}.
	 * @param ctx the parse tree
	 */
	void enterSamplerStateProperty(azslParser.SamplerStatePropertyContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#samplerStateProperty}.
	 * @param ctx the parse tree
	 */
	void exitSamplerStateProperty(azslParser.SamplerStatePropertyContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#literal}.
	 * @param ctx the parse tree
	 */
	void enterLiteral(azslParser.LiteralContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#literal}.
	 * @param ctx the parse tree
	 */
	void exitLiteral(azslParser.LiteralContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#leadingTypeFunctionSignature}.
	 * @param ctx the parse tree
	 */
	void enterLeadingTypeFunctionSignature(azslParser.LeadingTypeFunctionSignatureContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#leadingTypeFunctionSignature}.
	 * @param ctx the parse tree
	 */
	void exitLeadingTypeFunctionSignature(azslParser.LeadingTypeFunctionSignatureContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#hlslFunctionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterHlslFunctionDefinition(azslParser.HlslFunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#hlslFunctionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitHlslFunctionDefinition(azslParser.HlslFunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#hlslFunctionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterHlslFunctionDeclaration(azslParser.HlslFunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#hlslFunctionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitHlslFunctionDeclaration(azslParser.HlslFunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#userDefinedType}.
	 * @param ctx the parse tree
	 */
	void enterUserDefinedType(azslParser.UserDefinedTypeContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#userDefinedType}.
	 * @param ctx the parse tree
	 */
	void exitUserDefinedType(azslParser.UserDefinedTypeContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#associatedTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAssociatedTypeDeclaration(azslParser.AssociatedTypeDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#associatedTypeDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAssociatedTypeDeclaration(azslParser.AssociatedTypeDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#typedefStatement}.
	 * @param ctx the parse tree
	 */
	void enterTypedefStatement(azslParser.TypedefStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#typedefStatement}.
	 * @param ctx the parse tree
	 */
	void exitTypedefStatement(azslParser.TypedefStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#typealiasStatement}.
	 * @param ctx the parse tree
	 */
	void enterTypealiasStatement(azslParser.TypealiasStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#typealiasStatement}.
	 * @param ctx the parse tree
	 */
	void exitTypealiasStatement(azslParser.TypealiasStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#typeAliasingDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void enterTypeAliasingDefinitionStatement(azslParser.TypeAliasingDefinitionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#typeAliasingDefinitionStatement}.
	 * @param ctx the parse tree
	 */
	void exitTypeAliasingDefinitionStatement(azslParser.TypeAliasingDefinitionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#typeofExpression}.
	 * @param ctx the parse tree
	 */
	void enterTypeofExpression(azslParser.TypeofExpressionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#typeofExpression}.
	 * @param ctx the parse tree
	 */
	void exitTypeofExpression(azslParser.TypeofExpressionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#genericParameterList}.
	 * @param ctx the parse tree
	 */
	void enterGenericParameterList(azslParser.GenericParameterListContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#genericParameterList}.
	 * @param ctx the parse tree
	 */
	void exitGenericParameterList(azslParser.GenericParameterListContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#genericTypeDefinition}.
	 * @param ctx the parse tree
	 */
	void enterGenericTypeDefinition(azslParser.GenericTypeDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#genericTypeDefinition}.
	 * @param ctx the parse tree
	 */
	void exitGenericTypeDefinition(azslParser.GenericTypeDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#genericConstraint}.
	 * @param ctx the parse tree
	 */
	void enterGenericConstraint(azslParser.GenericConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#genericConstraint}.
	 * @param ctx the parse tree
	 */
	void exitGenericConstraint(azslParser.GenericConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#languageDefinedConstraint}.
	 * @param ctx the parse tree
	 */
	void enterLanguageDefinedConstraint(azslParser.LanguageDefinedConstraintContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#languageDefinedConstraint}.
	 * @param ctx the parse tree
	 */
	void exitLanguageDefinedConstraint(azslParser.LanguageDefinedConstraintContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDeclaration(azslParser.FunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#functionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDeclaration(azslParser.FunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#attributedFunctionDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterAttributedFunctionDeclaration(azslParser.AttributedFunctionDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#attributedFunctionDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitAttributedFunctionDeclaration(azslParser.AttributedFunctionDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterFunctionDefinition(azslParser.FunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#functionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitFunctionDefinition(azslParser.FunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#attributedFunctionDefinition}.
	 * @param ctx the parse tree
	 */
	void enterAttributedFunctionDefinition(azslParser.AttributedFunctionDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#attributedFunctionDefinition}.
	 * @param ctx the parse tree
	 */
	void exitAttributedFunctionDefinition(azslParser.AttributedFunctionDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#compilerExtensionStatement}.
	 * @param ctx the parse tree
	 */
	void enterCompilerExtensionStatement(azslParser.CompilerExtensionStatementContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#compilerExtensionStatement}.
	 * @param ctx the parse tree
	 */
	void exitCompilerExtensionStatement(azslParser.CompilerExtensionStatementContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#srgDefinition}.
	 * @param ctx the parse tree
	 */
	void enterSrgDefinition(azslParser.SrgDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#srgDefinition}.
	 * @param ctx the parse tree
	 */
	void exitSrgDefinition(azslParser.SrgDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#attributedSrgDefinition}.
	 * @param ctx the parse tree
	 */
	void enterAttributedSrgDefinition(azslParser.AttributedSrgDefinitionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#attributedSrgDefinition}.
	 * @param ctx the parse tree
	 */
	void exitAttributedSrgDefinition(azslParser.AttributedSrgDefinitionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#srgMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSrgMemberDeclaration(azslParser.SrgMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#srgMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSrgMemberDeclaration(azslParser.SrgMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#srgSemantic}.
	 * @param ctx the parse tree
	 */
	void enterSrgSemantic(azslParser.SrgSemanticContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#srgSemantic}.
	 * @param ctx the parse tree
	 */
	void exitSrgSemantic(azslParser.SrgSemanticContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#attributedSrgSemantic}.
	 * @param ctx the parse tree
	 */
	void enterAttributedSrgSemantic(azslParser.AttributedSrgSemanticContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#attributedSrgSemantic}.
	 * @param ctx the parse tree
	 */
	void exitAttributedSrgSemantic(azslParser.AttributedSrgSemanticContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#srgSemanticBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSrgSemanticBodyDeclaration(azslParser.SrgSemanticBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#srgSemanticBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSrgSemanticBodyDeclaration(azslParser.SrgSemanticBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#srgSemanticMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSrgSemanticMemberDeclaration(azslParser.SrgSemanticMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#srgSemanticMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSrgSemanticMemberDeclaration(azslParser.SrgSemanticMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#samplerBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSamplerBodyDeclaration(azslParser.SamplerBodyDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#samplerBodyDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSamplerBodyDeclaration(azslParser.SamplerBodyDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#samplerMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void enterSamplerMemberDeclaration(azslParser.SamplerMemberDeclarationContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#samplerMemberDeclaration}.
	 * @param ctx the parse tree
	 */
	void exitSamplerMemberDeclaration(azslParser.SamplerMemberDeclarationContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#maxAnisotropyOption}.
	 * @param ctx the parse tree
	 */
	void enterMaxAnisotropyOption(azslParser.MaxAnisotropyOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#maxAnisotropyOption}.
	 * @param ctx the parse tree
	 */
	void exitMaxAnisotropyOption(azslParser.MaxAnisotropyOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#minFilterOption}.
	 * @param ctx the parse tree
	 */
	void enterMinFilterOption(azslParser.MinFilterOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#minFilterOption}.
	 * @param ctx the parse tree
	 */
	void exitMinFilterOption(azslParser.MinFilterOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#magFilterOption}.
	 * @param ctx the parse tree
	 */
	void enterMagFilterOption(azslParser.MagFilterOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#magFilterOption}.
	 * @param ctx the parse tree
	 */
	void exitMagFilterOption(azslParser.MagFilterOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#mipFilterOption}.
	 * @param ctx the parse tree
	 */
	void enterMipFilterOption(azslParser.MipFilterOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#mipFilterOption}.
	 * @param ctx the parse tree
	 */
	void exitMipFilterOption(azslParser.MipFilterOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#reductionTypeOption}.
	 * @param ctx the parse tree
	 */
	void enterReductionTypeOption(azslParser.ReductionTypeOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#reductionTypeOption}.
	 * @param ctx the parse tree
	 */
	void exitReductionTypeOption(azslParser.ReductionTypeOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#comparisonFunctionOption}.
	 * @param ctx the parse tree
	 */
	void enterComparisonFunctionOption(azslParser.ComparisonFunctionOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#comparisonFunctionOption}.
	 * @param ctx the parse tree
	 */
	void exitComparisonFunctionOption(azslParser.ComparisonFunctionOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#addressUOption}.
	 * @param ctx the parse tree
	 */
	void enterAddressUOption(azslParser.AddressUOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#addressUOption}.
	 * @param ctx the parse tree
	 */
	void exitAddressUOption(azslParser.AddressUOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#addressVOption}.
	 * @param ctx the parse tree
	 */
	void enterAddressVOption(azslParser.AddressVOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#addressVOption}.
	 * @param ctx the parse tree
	 */
	void exitAddressVOption(azslParser.AddressVOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#addressWOption}.
	 * @param ctx the parse tree
	 */
	void enterAddressWOption(azslParser.AddressWOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#addressWOption}.
	 * @param ctx the parse tree
	 */
	void exitAddressWOption(azslParser.AddressWOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#minLodOption}.
	 * @param ctx the parse tree
	 */
	void enterMinLodOption(azslParser.MinLodOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#minLodOption}.
	 * @param ctx the parse tree
	 */
	void exitMinLodOption(azslParser.MinLodOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#maxLodOption}.
	 * @param ctx the parse tree
	 */
	void enterMaxLodOption(azslParser.MaxLodOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#maxLodOption}.
	 * @param ctx the parse tree
	 */
	void exitMaxLodOption(azslParser.MaxLodOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#mipLodBiasOption}.
	 * @param ctx the parse tree
	 */
	void enterMipLodBiasOption(azslParser.MipLodBiasOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#mipLodBiasOption}.
	 * @param ctx the parse tree
	 */
	void exitMipLodBiasOption(azslParser.MipLodBiasOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#borderColorOption}.
	 * @param ctx the parse tree
	 */
	void enterBorderColorOption(azslParser.BorderColorOptionContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#borderColorOption}.
	 * @param ctx the parse tree
	 */
	void exitBorderColorOption(azslParser.BorderColorOptionContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#filterModeEnum}.
	 * @param ctx the parse tree
	 */
	void enterFilterModeEnum(azslParser.FilterModeEnumContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#filterModeEnum}.
	 * @param ctx the parse tree
	 */
	void exitFilterModeEnum(azslParser.FilterModeEnumContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#reductionTypeEnum}.
	 * @param ctx the parse tree
	 */
	void enterReductionTypeEnum(azslParser.ReductionTypeEnumContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#reductionTypeEnum}.
	 * @param ctx the parse tree
	 */
	void exitReductionTypeEnum(azslParser.ReductionTypeEnumContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#addressModeEnum}.
	 * @param ctx the parse tree
	 */
	void enterAddressModeEnum(azslParser.AddressModeEnumContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#addressModeEnum}.
	 * @param ctx the parse tree
	 */
	void exitAddressModeEnum(azslParser.AddressModeEnumContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#comparisonFunctionEnum}.
	 * @param ctx the parse tree
	 */
	void enterComparisonFunctionEnum(azslParser.ComparisonFunctionEnumContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#comparisonFunctionEnum}.
	 * @param ctx the parse tree
	 */
	void exitComparisonFunctionEnum(azslParser.ComparisonFunctionEnumContext ctx);
	/**
	 * Enter a parse tree produced by {@link azslParser#borderColorEnum}.
	 * @param ctx the parse tree
	 */
	void enterBorderColorEnum(azslParser.BorderColorEnumContext ctx);
	/**
	 * Exit a parse tree produced by {@link azslParser#borderColorEnum}.
	 * @param ctx the parse tree
	 */
	void exitBorderColorEnum(azslParser.BorderColorEnumContext ctx);
}