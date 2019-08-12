package com.ibm.furniturestore;

import android.net.Uri;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.ar.core.Anchor;
import com.google.ar.sceneform.AnchorNode;
import com.google.ar.sceneform.rendering.ModelRenderable;
import com.google.ar.sceneform.ux.ArFragment;
import com.google.ar.sceneform.ux.TransformableNode;

public class ARActivity extends AppCompatActivity {

    private ArFragment arFragment;
    Button btnChair, btnCornerTable, btnTable, btnRedSofa, btnBlackSofa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ar);

        btnChair = findViewById(R.id.btnChair);
        btnCornerTable = findViewById(R.id.btnCornerTable);
        btnTable = findViewById(R.id.btnTable);
        btnRedSofa = findViewById(R.id.btnRedSofa);
        btnBlackSofa = findViewById(R.id.btnBlackSofa);

        arFragment = (ArFragment) getSupportFragmentManager().findFragmentById(R.id.arFragment);

        DrawObject("Chair.sfb");

        btnChair.setOnClickListener(v -> DrawObject("Chair.sfb"));

        btnCornerTable.setOnClickListener(v -> DrawObject("corner_table.sfb"));

        btnTable.setOnClickListener(v -> DrawObject("table.sfb"));

        btnRedSofa.setOnClickListener(v -> DrawObject("red_sofa.sfb"));

        btnBlackSofa.setOnClickListener(v -> DrawObject("black_couch.sfb"));

    }

    private void DrawObject(String objectName) {
        Toast toast=Toast.makeText(getApplicationContext(),objectName+" Selected. Now Tap on the Anchor points to view the Object.",Toast.LENGTH_LONG);
        toast.show();
        arFragment.setOnTapArPlaneListener((hitResult, plane, motionEvent) -> {
            // Fixed location & render overlay like marker
            Anchor anchor = hitResult.createAnchor();

            ModelRenderable.builder()
                    .setSource(this, Uri.parse(objectName.toString()))
                    .build()
                    .thenAccept(modelRenderable -> addModelToScene(anchor, modelRenderable))
                    .exceptionally(throwable -> {
                        AlertDialog.Builder builder = new AlertDialog.Builder(this);
                        builder.setMessage(throwable.getMessage()).show();
                        return null;
                    });
        });
    }

    private void addModelToScene(Anchor anchor, ModelRenderable modelRenderable) {
        AnchorNode anchorNode = new AnchorNode(anchor);
        TransformableNode transformableNode = new TransformableNode(arFragment.getTransformationSystem());
        transformableNode.setParent(anchorNode);
        transformableNode.setRenderable(modelRenderable);
        arFragment.getArSceneView().getScene().addChild(anchorNode);
        transformableNode.select();
    }
}
