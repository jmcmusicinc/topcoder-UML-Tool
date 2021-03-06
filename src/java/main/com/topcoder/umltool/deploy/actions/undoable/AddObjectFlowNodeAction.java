/*
 * Copyright (C) 2007 TopCoder Inc., All Rights Reserved.
 */
package com.topcoder.umltool.deploy.actions.undoable;

import java.awt.Dimension;
import java.awt.Point;

import com.topcoder.diagraminterchange.GraphNode;
import com.topcoder.diagraminterchange.Uml1SemanticModelBridge;
import com.topcoder.gui.diagramviewer.DiagramView;
import com.topcoder.gui.diagramviewer.uml.activityelements.ObjectFlowNode;
import com.topcoder.uml.actions.diagram.elements.AddDiagramElementAction;
import com.topcoder.uml.actions.model.activity.AddObjectFlowStateAction;
import com.topcoder.uml.model.activitygraphs.ActivityGraph;
import com.topcoder.uml.model.activitygraphs.ObjectFlowState;
import com.topcoder.uml.model.activitygraphs.ObjectFlowStateImpl;
import com.topcoder.uml.modelmanager.UMLModelManager;
import com.topcoder.umltool.deploy.DeployHelper;
import com.topcoder.umltool.deploy.MainFrame;

/**
 * <p>
 * This action is used to add an ObjectFlowNode to the diagram view. It will create model element and graph element,
 * then add them to uml model manager and diagram first. This action is an UndoableAction.
 * </p>
 * @author ly, FireIce, ylouis
 * @version 1.0
 */
public class AddObjectFlowNodeAction extends AddNodeAction {

    /**
     * <p>
     * Represents the presentation name.
     * </p>
     */
    private static final String PRESENTATION_NAME = "Add ObjectFlowNode";

    /**
     * <p>
     * Represents the uml model manager.
     * </p>
     */
    private UMLModelManager modelManager;

    /**
     * <p>
     * Position of the graph node for this node.
     * </p>
     */
    private Point position;

    /**
     * <p>
     * Size of the graph node for this node.
     * </p>
     */
    private Dimension size;

    /**
     * <p>
     * Name of the model element for this node.
     * </p>
     */
    private String name;

    /**
     * <p>
     * Model element for this node.
     * </p>
     */
    private ObjectFlowState element;

    /**
     * <p>
     * Graph node for this node.
     * </p>
     */
    private GraphNode graphNode;

    /**
     * <p>
     * Creates an instance of <code>AddObjectFlowNodeAction</code>.
     * </p>
     * @param mainFrame
     *            main frame window
     * @param view
     *            diagram view which should contain the new node
     * @param position
     *            position of the graph node for added node
     * @param size
     *            size of the graph node for added node
     * @param name
     *            name of the model element for added node
     */
    public AddObjectFlowNodeAction(MainFrame mainFrame, DiagramView view, Point position, Dimension size, String name) {
        super(PRESENTATION_NAME, mainFrame, view);
        this.position = position;
        this.size = size;
        this.name = name;

        modelManager = UMLModelManager.getInstance();

        createModelElement();
        createGraphNode();
        createNode();
    }

    /**
     * <p>
     * Creates an instance of <code>AddObjectFlowNodeAction</code>.
     * </p>
     * @param mainFrame
     *            main frame window
     * @param view
     *            diagram view which should contain the new node
     * @param position
     *            position of the graph node for added node
     * @param size
     *            size of the graph node for added node
     * @param element
     *            specified model element for added node
     */
    public AddObjectFlowNodeAction(MainFrame mainFrame, DiagramView view, Point position, Dimension size,
        ObjectFlowState element) {
        super(PRESENTATION_NAME, mainFrame, view);
        this.position = position;
        this.size = size;
        this.name = element.getName();
        this.element = element;

        modelManager = UMLModelManager.getInstance();

        createModelElement();
        createGraphNode();
        createNode();
    }

    /**
     * <p>
     * Creates the model element for new node.
     * </p>
     */
    protected void createModelElement() {
        if (element == null) {
            element = new ObjectFlowStateImpl();
            element.setName(name);
            Uml1SemanticModelBridge bridge = (Uml1SemanticModelBridge) getDiagramView().getDiagram().getOwner();
            setAddModelElementAction(new AddObjectFlowStateAction(element, (ActivityGraph) bridge.getElement(),
                    modelManager));
        }
    }

    /**
     * <p>
     * Creates the graph node for new node.
     * </p>
     */
    protected void createGraphNode() {
        graphNode = DeployHelper.createGraphNodeForActivityState(element, position, size);

        setAddDiagramElementAction(new AddDiagramElementAction(getDiagramView().getDiagram(), graphNode, modelManager
                .getProjectConfigurationManager()));
    }

    /**
     * <p>
     * Creates a new ObjectFlowNode.
     * </p>
     */
    protected void createNode() {
        ObjectFlowNode node = DeployHelper.createObjectFlowNode(getMainFrame(), graphNode);
        setNode(node);
    }
}
